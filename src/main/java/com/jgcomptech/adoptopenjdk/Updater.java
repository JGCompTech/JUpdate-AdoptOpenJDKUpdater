package com.jgcomptech.adoptopenjdk;

import com.jgcomptech.adoptopenjdk.api.Version;
import com.jgcomptech.adoptopenjdk.api.beans.SimpleAsset;
import com.jgcomptech.adoptopenjdk.enums.AssetJVMType;
import com.jgcomptech.adoptopenjdk.enums.AssetReleaseType;
import com.jgcomptech.adoptopenjdk.enums.DLStatus;
import com.jgcomptech.adoptopenjdk.utils.HTTPDownload;
import com.jgcomptech.adoptopenjdk.utils.osutils.ExecutingCommand;
import com.jgcomptech.adoptopenjdk.utils.osutils.windows.Registry;
import com.jgcomptech.adoptopenjdk.utils.progressbar.ProgressBar;
import com.jgcomptech.adoptopenjdk.utils.progressbar.ProgressBarBuilder;
import com.jgcomptech.adoptopenjdk.utils.progressbar.ProgressBarStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

import static com.jgcomptech.adoptopenjdk.Settings.*;
import static com.jgcomptech.adoptopenjdk.utils.Literals.FILE_SEPARATOR;
import static com.jgcomptech.adoptopenjdk.utils.info.OSInfo.isWindows;
import static com.jgcomptech.adoptopenjdk.utils.osutils.windows.Registry.HKEY.LOCAL_MACHINE;

/**
 * The main updater class that checks to see if a update is required.
 */
public class Updater {
    private final Logger logger = LoggerFactory.getLogger(Updater.class);
    private final Map<String, Version> installed = new HashMap<>();
    private final Version latestVersion;
    private Version currentVersion = new Version("0.0.0+0.0");
    private boolean needsUpdate;
    private boolean isInstalled;
    private final SubRelease release;

    /**
     * Instantiates a new Updater.
     * @param release the release
     * @param asset   the asset
     * @throws IOException if an error occurs
     */
    public Updater(final SubRelease release, final SimpleAsset asset) throws IOException {
        this.release = release;

        if(isWindows()) {
            getVersionStringsFromRegistry().forEach(v -> installed.put(v.getBasic(), v));
            getVersionStringsFromProgramFiles().forEach(v -> installed.put(v.getBasic(), v));
        }

        latestVersion = asset.getVersion();

        for (final Map.Entry<String, Version> version : installed.entrySet()) {
            if(version.getValue().getMajor() == latestVersion.getMajor()) {
                isInstalled = true;
                currentVersion = version.getValue();
                if(!latestVersion.isEqualTo(currentVersion)) {
                    needsUpdate = latestVersion.isNewerThen(currentVersion);
                }
            }
        }

        if(!isInstalled) needsUpdate = true;
    }

    /**
     * Gets the latest version.
     * @return the latest version
     */
    public Version getLatestVersion() {
        return latestVersion;
    }

    /**
     * Gets the currently installed version.
     * @return the currently installed version
     */
    public Version getCurrentVersion() {
        return currentVersion;
    }

    /**
     * Returns true if an update is required.
     * @return true if an update is required
     */
    public boolean needsUpdate() {
        return needsUpdate;
    }

    /**
     * Returns true if the specified base Java version is installed.
     * @return true if the specified base Java version is installed
     */
    public boolean isInstalled() {
        return isInstalled;
    }

    /**
     * Returns a list of all installed releases.
     * @return a list of all installed releases
     */
    public List<Version> getInstalledReleases() {
        return new ArrayList<>(installed.values());
    }

    /**
     * Returns a list of all installed releases from the Windows registry.
     * @return a list of all installed releases from the Windows registry
     */
    public List<Version> getVersionStringsFromRegistry() {
        String path = release.getReleaseType() == AssetReleaseType.JDK ? JDK_REGISTRY_PATH : JRE_REGISTRY_PATH;

        boolean exists = Registry.keyExists(LOCAL_MACHINE, path);

        if(exists) {
            List<String> results = Registry.getKeys(LOCAL_MACHINE, path);
            List<Version> versions = new ArrayList<>();
            for (String r : results) {
                boolean matches = false;

                if (release.getJvmType() == AssetJVMType.Hotspot
                        && Registry.keyExists(LOCAL_MACHINE, path + r + "\\hotspot")) {
                    matches = true;
                } else if (release.getJvmType() == AssetJVMType.OpenJ9
                        && Registry.keyExists(LOCAL_MACHINE, path + r + "\\openj9")) {
                    matches = true;
                }

                if(matches) versions.add(new Version(r, true));
            }
            return versions;
        }
        return new ArrayList<>();
    }

    /**
     * Returns a list of all installed releases from the Windows ProgramFiles folder.
     * @return a list of all installed releases from the Windows ProgramFiles folder
     * @throws IOException if an error occurs
     */
    public List<Version> getVersionStringsFromProgramFiles() throws IOException {
        final Path directory = Paths.get(WINDOWS_DEFAULT_INSTALL_PATH);

        final List<Version> versions = new ArrayList<>();

        final List<Path> files = Files.find(directory, Integer.MAX_VALUE,
                (path, attributes) -> attributes.isDirectory())
                .collect(Collectors.toList());

        for (final Path file : files) {
            String name = file.toFile().getName();

            boolean matches = false;

            if (name.startsWith("jdk-") && release.getReleaseType() == AssetReleaseType.JDK) {
                if (name.endsWith("-hotspot") && release.getJvmType() == AssetJVMType.Hotspot
                        || name.endsWith("-openj9") && release.getJvmType() == AssetJVMType.OpenJ9) {
                    matches = true;
                }
            } else if (name.startsWith("jre-") && release.getReleaseType() == AssetReleaseType.JRE) {
                if (name.endsWith("-hotspot") && release.getJvmType() == AssetJVMType.Hotspot
                        || name.endsWith("-openj9") && release.getJvmType() == AssetJVMType.OpenJ9) {
                    matches = true;
                }
            }

            if (matches) versions.add(new Version(name, false, true));
        }

        return versions;
    }

    /**
     * Downloads the specified file to the specified path.
     * @param path the path to download to
     * @param url  the url to download
     * @return the filename if no errors occurred
     */
    @SuppressWarnings("BusyWait")
    public Optional<String> runDownload(final String path, final String url) {
        String newPath = path;
        boolean downloadSucceeded;

        while (newPath.endsWith(FILE_SEPARATOR)) {
            newPath = newPath.substring(0, path.length() - 1);
        }

        try(final HTTPDownload download = new HTTPDownload(path, url).download()) {
            while(download.getSize() == -1) Thread.sleep(10);
            logger.info("Downloading installer...");
            try (final ProgressBar pb = new ProgressBarBuilder()
                    .setTaskName("")
                    .setInitialMax(download.getSize())
                    .setStyle(ProgressBarStyle.UNICODE_BLOCK)
                    .useFileProgressBarRenderer()
                    .build()) {
                while(download.getStatus() == DLStatus.DOWNLOADING) {
                    pb.stepTo(download.getDownloaded());
                }
            }

            switch (download.getStatus()) {
                case COMPLETE:
                    final File file = new File(download.getFilepath());
                    if(file.exists() && !file.isDirectory()) {
                        logger.info("Download Complete!");
                        downloadSucceeded = true;
                    } else {
                        logger.error("Installer Failed To Download!");
                        downloadSucceeded = false;
                    }
                    break;
                case CANCELLED:
                    logger.info("Download Canceled!");
                    downloadSucceeded = false;
                    break;
                case ERROR:
                    logger.info("Download Failed!");
                    downloadSucceeded = false;
                    break;
                default:
                    downloadSucceeded = false;
                    break;
            }

        } catch (final InterruptedException | MalformedURLException e) {
            logger.error(e.getMessage(), e);
            downloadSucceeded = false;
        }

        if(downloadSucceeded) {
            String filename;

            if(path.isEmpty()) {
                filename = new File(url).getName();
            } else {
                filename = path + FILE_SEPARATOR + new File(url).getName();
            }

            return Optional.of(filename);
        } else return Optional.empty();
    }

    /**
     * Runs the installer.
     * @param filename the filename
     * @throws FileNotFoundException if an error occurs
     */
    public void runInstall(final String filename) throws FileNotFoundException {
        final File localFile = new File(filename);
        if(localFile.exists() && !localFile.isDirectory()) {
            if(isWindows()) {
                logger.info("Running installer...");
                try {
                    ExecutingCommand.runNewCmd("msiexec /i \"" + filename + "\"");
                } catch (final InterruptedException | IOException e) {
                    throw new IllegalStateException(e);
                }
            } else {
                throw new IllegalStateException("Cannot Install File: Unsupported OS!");
            }
        } else {
            throw new FileNotFoundException("Cannot Install File: " + filename + ", File Not Found!");
        }
    }
}
