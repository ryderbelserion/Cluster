package com.ryderbelserion.cluster.api.config;

import com.google.gson.GsonBuilder;
import com.ryderbelserion.cluster.api.config.types.FileType;
import java.io.File;
import java.nio.file.Path;

public abstract class FileEngine {

    private final String fileName;
    private final Path filePath;
    private final FileType fileType;

    private GsonBuilder gson;

    public FileEngine(String fileName, Path filePath, FileType fileType) {
        this.fileName = fileName;
        this.filePath = filePath;
        this.fileType = fileType;
    }

    public void setGsonBuilder(GsonBuilder gson) {
        this.gson = gson;
    }

    public FileType getFileType() {
        return this.fileType;
    }

    public GsonBuilder getGson() {
        return this.gson;
    }

    public Path getFilePath() {
        return this.filePath;
    }

    public File getFile() {
        return new File(this.filePath.toFile(), this.fileName);
    }
}