package com.jgcomptech.adoptopenjdk;

import com.jgcomptech.adoptopenjdk.api.Version;
import com.jgcomptech.adoptopenjdk.api.beans.SimpleAsset;
import com.jgcomptech.adoptopenjdk.enums.AssetJVMType;
import com.jgcomptech.adoptopenjdk.enums.AssetReleaseType;
import com.jgcomptech.adoptopenjdk.enums.DLStatus;
import com.jgcomptech.adoptopenjdk.utils.Download;
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
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

import static com.jgcomptech.adoptopenjdk.Settings.*;
import static com.jgcomptech.adoptopenjdk.utils.Literals.FILE_SEPARATOR;
import static com.jgcomptech.adoptopenjdk.utils.info.OSInfo.isWindows;
import static com.jgcomptech.adoptopenjdk.utils.osutils.windows.Registry.HKEY.LOCAL_MACHINE;

public class Updater {
    private final Logger logger = LoggerFactory.getLogger(Updater.class);
    private final Map<String, Version> installed = new HashMap<>();
    private final Version latestVersion;
    private Version currentVersion = new Version("0.0.0+0.0");
    private boolean needsUpdate;
    private boolean isInstalled;
    final SubRelease release;

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

    public Version getLatestVersion() {
        return latestVersion;
    }

    public Version getCurrentVersion() {
        return currentVersion;
    }

    public boolean needsUpdate() {
        return needsUpdate;
    }

    public boolean isInstalled() {
        return isInstalled;
    }

    public List<Version> getInstalledReleases() {
        return new ArrayList<>(installed.values());
    }

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

    public boolean download(final String path, final String url) {
        try {
            return download(path, new URL(url));
        } catch (final MalformedURLException e) {
            logger.error(e.getMessage(), e);
            return false;
        }
    }

    @SuppressWarnings("BusyWait")
    public boolean download(final String path, final URL url) {
        try {
            final Download download = new Download(path, url);
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
                        return true;
                    } else {
                        logger.error("Installer Failed To Download!");
                        return false;
                    }
                case CANCELLED:
                    logger.info("Download Canceled!");
                    return false;
                case ERROR:
                    logger.info("Download Failed!");
                    return false;
                default:
                    return false;
            }

        } catch (final InterruptedException e) {
            logger.error(e.getMessage(), e);
            return false;
        }
    }

    public Optional<String> runDownload(final String path, final String url) {
        String newPath = path;

        while (newPath.endsWith(FILE_SEPARATOR)) {
            newPath = newPath.substring(0, path.length() - 1);
        }

        if(download(path, url)) {
            String filename;

            if(path.isEmpty()) {
                filename = new File(url).getName();
            } else {
                filename = path + FILE_SEPARATOR + new File(url).getName();
            }

            return Optional.of(filename);
        } else return Optional.empty();
    }

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
