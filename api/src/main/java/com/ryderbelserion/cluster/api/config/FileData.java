package com.ryderbelserion.cluster.api.config;

import java.io.File;
import java.nio.file.Path;

public abstract class FileData {

    private final String name;
    private final Path path;

    private final boolean isData;

    public FileData(String name, String path) {
        this.name = name;
        this.path = Path.of(path);

        this.isData = false;
    }

    public FileData(String name, String path, boolean isData) {
        this.name = name;
        this.path = Path.of(path);

        this.isData = isData;
    }

    public File getFile() {
        return new File(this.path.toFile(), this.name);
    }

    public boolean isData() {
        return this.isData;
    }
}