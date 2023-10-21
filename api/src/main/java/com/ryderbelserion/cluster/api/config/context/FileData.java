package com.ryderbelserion.cluster.api.config.context;

import com.google.gson.GsonBuilder;
import java.io.File;
import java.nio.file.Path;

public abstract class FileData {

    private FileType type;

    private String name;
    private Path path;

    private boolean isData;

    public FileData(FileType type, String name, String path) {
        this.type = type;

        this.name = name;
        this.path = Path.of(path);
    }

    public FileData(FileType type, String name, String path, boolean isData) {
        this.type = type;

        this.name = name;
        this.path = Path.of(path);

        if (this.type == FileType.json) this.isData = isData;
    }

    private GsonBuilder gson;

    public FileData(FileType type, String name, String path, GsonBuilder gson) {
        if (type != FileType.json) return;

        this.gson = gson;

        this.type = type;

        this.name = name;
        this.path = Path.of(path);
    }

    public File getFile() {
        return new File(this.path.toFile(), this.name);
    }

    public FileType getType() {
        return this.type;
    }

    public void setGson(GsonBuilder gson) {
        this.gson = gson;
    }

    public GsonBuilder getGson() {
        return this.gson;
    }

    public boolean isData() {
        return this.isData;
    }
}