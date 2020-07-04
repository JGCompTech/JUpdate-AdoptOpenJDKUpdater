package com.jgcomptech.adoptopenjdk;

import java.util.InputMismatchException;

import static com.jgcomptech.adoptopenjdk.AssetFileType.NONE;

@SuppressWarnings("FieldNotUsedInToString")
public class Filename {
    private final String fullName;
    private final String baseName;
    private final String extension;
    private final AssetFileType fileType;

    public Filename(final String fullFilename) {
        fullName = fullFilename;
        fileType = AssetFileType.parseFromName(fullFilename);

        if(fileType == NONE) throw new InputMismatchException("Filename extension not recognized! " + fullFilename);
        baseName = fullFilename.replace('.' + fileType.name(), "");
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
