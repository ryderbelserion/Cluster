package com.ryderbelserion.cluster.api.config;

import com.ryderbelserion.cluster.api.config.types.json.JsonFile;
import java.io.File;

public class FileManager implements FileContext {

    private JsonFile jsonFile;

    @Override
    public void addFile(FileData file) {
        this.jsonFile = new JsonFile(file);
        this.jsonFile.loadFile();
    }

    @Override
    public void saveFile(FileData file) {
        this.jsonFile = new JsonFile(file);
        this.jsonFile.saveFile();
    }

    @Override
    public void removeFile(FileData file) {
        this.jsonFile = new JsonFile(file);
        this.jsonFile.removeFile();
    }

    @Override
    public File getFile(FileData file) {
        return file.getFile();
    }
}