package com.jgcomptech.adoptopenjdk.api;

import com.jgcomptech.adoptopenjdk.enums.AssetFileType;

import java.util.InputMismatchException;
import java.util.Locale;

import static com.jgcomptech.adoptopenjdk.enums.AssetFileType.NONE;

@SuppressWarnings("FieldNotUsedInToString")
public class Filename {
    private final String fullName;
    private final String baseName;
    private final String extension;
    private final AssetFileType fileType;

    public Filename(final String fullFilename) {
        //Fix naming issue with Java 9 OpenJ9
        if(!fullFilename.contains("zip.sha256.txt")
                && fullFilename.contains("sha256.txt")
                && fullFilename.contains("Windows")) {
            fullName = fullFilename.replace("sha256.txt", "zip.sha256.txt");
        } else if(!fullFilename.contains("tar.gz.sha256.txt")
                && fullFilename.contains("sha256.txt")
                && (fullFilename.contains("Linux")
                    || fullFilename.contains("AIX"))) {
            fullName = fullFilename.replace("sha256.txt", "tar.gz.sha256.txt");
        } else fullName = fullFilename;

        fileType = AssetFileType.parseFromName(fullName);

        if(fileType == NONE) throw new InputMismatchException("Filename extension not recognized! " + fullName);
        baseName = fullName.replace('.' + fileType.getValue(), "").toLowerCase(Locale.getDefault());
        extension = fileType.name();
    }

    public String getFullName() {
        return fullName;
    }

    public String getBaseName() {
        return baseName;
    }

    public String getExtension() {
        return extension;
    }

    public AssetFileType getFileType() {
        return fileType;
    }

    @Override
    public String toString() {
        return fullName;
    }
}
