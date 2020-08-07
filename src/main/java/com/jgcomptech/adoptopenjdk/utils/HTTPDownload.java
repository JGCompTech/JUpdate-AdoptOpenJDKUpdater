package com.jgcomptech.adoptopenjdk.utils;

import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.jgcomptech.adoptopenjdk.enums.DLStatus;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Observable;
import java.util.Optional;
import java.util.function.Supplier;

import static com.jgcomptech.adoptopenjdk.enums.DLStatus.*;
import static com.jgcomptech.adoptopenjdk.utils.Utils.isBoolean;

public class HTTPDownload extends Observable implements AutoCloseable, Runnable {
    // Max size of download buffer.
    private static final int MAX_BUFFER_SIZE = 1024;

    private final URL url; // download URL
    private String path;
    private int size; // size of download in bytes
    private int downloaded; // number of bytes downloaded
    private DLStatus status; // current status of download
    private String errorMessage; //the error message if an error occurs
    private String filename;
    private String filepath;
    private RandomAccessFile file = null;
    private InputStream stream = null;
    private HttpURLConnection connection;

    private enum Type {
        FileDownload,
        JSONDownload,
        TextDownload,
        BooleanDownload
    }

    public HTTPDownload(final String url) throws MalformedURLException {
        this("", new URL(url));
    }

    public HTTPDownload(final URL url) {
        this("", url);
    }

    public HTTPDownload(final String path, final String url) throws MalformedURLException {
        this(path, new URL(url));
    }

    // Constructor for Download.
    public HTTPDownload(final String path, final URL url) {
        this.path = path;

        while (this.path.endsWith("/")) {
            this.path = this.path.substring(0, this.path.length() - 1);
        }

        if(!this.path.isEmpty()) this.path = this.path + '/';

        this.url = url;
    }

    // Get this download's URL.
    public String getUrl() {
        return url.toString();
    }

    // Get this download's size.
    public int getSize() {
        return size;
    }

    //Get total size downloaded
    public int getDownloaded() {
        return downloaded;
    }

    // Get this download's progress.
    public float getProgress() {
        return ((float) downloaded / size) * 100;
    }

    // Get this download's status.
    public DLStatus getStatus() {
        return status;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public String getFilename() {
        return filename;
    }

    //Gets the path of the folder to download to.
    public String getPath() {
        return path;
    }

    public String getFilepath() {
        return filepath;
    }

    // Pause this download.
    public HTTPDownload pause() {
        status = PAUSED;
        stateChanged();
        return this;
    }

    // Resume this download.
    public HTTPDownload resume() {
        status = DOWNLOADING;
        stateChanged();
        return download();
    }

    // Cancel this download.
    public HTTPDownload cancel() {
        status = CANCELLED;
        stateChanged();
        return this;
    }

    // Mark this download as having an error.
    private void error(final String errorMessage) {
        status = ERROR;
        this.errorMessage = errorMessage;
        stateChanged();
    }

    @Override
    public void close() {
        // Close file.
        if (file != null) {
            try {
                file.close();
            } catch (Exception ignored) {}
        }

        // Close connection to server.
        if (stream != null) {
            try {
                stream.close();
            } catch (Exception ignored) {}
        }
    }

    // Start or resume downloading.
    public HTTPDownload download() {
        preStart();

        new Thread(this).start();

        return this;
    }

    private void connect() throws IOException {
        // Open connection to URL.
        connection = (HttpURLConnection) url.openConnection();

        // Specify what portion of file to download.
        connection.setRequestProperty("Range", "bytes=" + downloaded + "-");

        // Connect to server.
        connection.connect();
    }

    // Download file.
    public void run() {
        Path filePath;

        try {
            connect();

            // Make sure response code is in the 200 range.
            processResponseCode(connection.getResponseCode(), Type.FileDownload);

            // Check for valid content length.
            int contentLength = connection.getContentLength();
            if (contentLength < 1) error("Invalid Content Length!");

            //Set the size for this download if it hasn't been already set.
            if (size == -1) {
                size = contentLength;
                stateChanged();
            }

            filename = parseFilename(url);
            filepath = path + filename;

            // Open file and seek to the end of it.
            filePath = Paths.get(filepath);

            file = new RandomAccessFile(filePath.toFile(), "rw");
            file.seek(downloaded);

            stream = connection.getInputStream();
            while (status == DOWNLOADING) {
                // Size buffer according to how much of the file is left to download.
                byte[] buffer = size - downloaded > MAX_BUFFER_SIZE
                        ? new byte[MAX_BUFFER_SIZE]
                        : new byte[size - downloaded];

                // Read from server into buffer.
                int read = stream.read(buffer);
                if (read == -1) break;

                // Write buffer to file.
                file.write(buffer, 0, read);
                downloaded += read;
                stateChanged();
            }

            checkIfComplete();
        } catch (final Exception e) {
            error(e.getMessage());
        }
    }

    public Optional<String> processTextAsString() throws IOException {
        return process(this::getHTTPResponseAsString, Type.TextDownload);
    }

    public Optional<Boolean> processTextAsBoolean() throws IOException {
        return process(() -> isBoolean(getHTTPResponseAsString()), Type.BooleanDownload);
    }

    public Optional<JsonArray> processJSONAsArray() throws IOException {
        return process(this::getHTTPResponseAsJSONArray, Type.JSONDownload);
    }

    private <T> Optional<T> process(final Supplier<T> task, Type downloadType) throws IOException {
        Optional<T> result = Optional.empty();

        if(!preStart()) return Optional.empty();

        connect();

        if(processResponseCode(connection.getResponseCode(), downloadType)) {
            result = Optional.ofNullable(task.get());
        }

        checkIfComplete();

        return result;
    }

    private String getHTTPResponseAsString() {
        try {
            stream = connection.getInputStream();
            String encoding = connection.getContentEncoding();
            encoding = encoding == null ? "UTF-8" : encoding;
            return IOUtils.toString(stream, encoding);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    private JsonArray getHTTPResponseAsJSONArray() {
        try {
            stream = connection.getInputStream();
            try (final InputStreamReader isr = new InputStreamReader(stream)) {
                return JsonParser.parseReader(isr).getAsJsonArray();
            }
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    private String getHTTPErrorMessage() {
        try {
            try (final InputStreamReader isr = new InputStreamReader(connection.getErrorStream())) {
                return JsonParser.parseReader(isr).getAsJsonObject()
                        .get("message").getAsString().replace("\"", "");
            }
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    private boolean processResponseCode(final int code, final Type type) {
        switch(code) {
            case 200:
            case 201:
            case 202:
            case 203:
            case 204:
            case 205:
            case 206:
            case 207:
            case 208:
                return true;
            case 400:
                error("Bad Request!");
                break;
            case 401:
                error(type == Type.JSONDownload ? "GitHub API Bad Credentials!" : "Unauthorized Or Bad Credentials!");
                break;
            case 403:
                if(type == Type.JSONDownload) {
                    error("GitHub API Rate Limit Reached!");
                    throw new IllegalStateException("GitHub API Rate Limit Reached!");
                }
                error("Forbidden!");
                break;
            case 404:
                if(type == Type.JSONDownload) {
                    String message = getHTTPErrorMessage();

                    error(message.contains("Not Found") ? "GitHub Page Not Found!" : "GitHub API: " + message);
                } else error("Not Found!");
                break;
            case 500:
                error("Internal Server Error!");
                break;
            case 501:
                error("Not Implemented!");
                break;
            case 502:
                error("Bad Gateway!");
                break;
            case 503:
                error("Service Unavailable!");
                break;
            case 504:
                error("Gateway Timeout!");
                break;
            default:
                String message = getHTTPErrorMessage();

                error(type == Type.JSONDownload ? "Unknown API Error! Error Message: " + message : message);

                break;
        }

        return false;
    }

    // Notify observers that this download's status has changed.
    private void stateChanged() {
        setChanged();
        notifyObservers();
    }

    // Get file name portion of URL.
    private String parseFilename(URL url) {
        String fileName = url.getFile();
        return fileName.substring(fileName.lastIndexOf('/') + 1);
    }

    private boolean preStart() {
        if(status == DOWNLOADING) return false;

        size = -1;
        downloaded = 0;
        status = DOWNLOADING;

        return true;
    }

    private void checkIfComplete() {
        // Change status to complete if this point was reached because downloading has finished.
        if (status == DOWNLOADING) {
            status = COMPLETE;
            stateChanged();
        }
    }
}
