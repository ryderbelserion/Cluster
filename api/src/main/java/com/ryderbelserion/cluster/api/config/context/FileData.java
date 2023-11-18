package com.ryderbelserion.cluster.api.config.context;

import com.google.gson.GsonBuilder;
import java.io.File;
import java.nio.file.Path;

public abstract class FileData {

    private FileType type;

    private String name;
    private Path path;

    private boolean isData;

    public FileData(FileType type, String name, Path path) {
        this.type = type;

        this.name = name;
        this.path = path;
    }

    public FileData(FileType type, String name, Path path, boolean isData) {
        this.type = type;

        this.name = name;
        this.path = path;

        if (this.type == FileType.json) this.isData = isData;
    }

    private GsonBuilder gson;

    public FileData(FileType type, String name, Path path, GsonBuilder gson) {
        if (type != FileType.json) return;

        this.gson = gson;

        this.type = type;

        this.name = name;
        this.path = path;
    }

    public File getFile() {
        return new File(this.path.toFile(), this.name);
    }

    public FileType getType() {
        return this.type;
    }

    public void setGson(GsonBuilder gson) {
        if (this.type != FileType.json) return;

        this.gson = gson;
    }

    public GsonBuilder getGson() {
        return this.gson;
    }

    public boolean isData() {
        return this.isData;
    }
}