package com.ryderbelserion.cluster.api.config;

import com.ryderbelserion.cluster.api.config.types.file.JsonFile;
import java.io.File;

public class FileManager implements FileContext {

    private JsonFile jsonFile;

    @Override
    public void addFile(FileEngine file) {
        switch (file.getFileType()) {
            case json -> {
                this.jsonFile = new JsonFile(file);
                this.jsonFile.load();
            }
        }
    }

    @Override
    public void saveFile(FileEngine file) {
        switch (file.getFileType()) {
            case json -> {
                this.jsonFile = new JsonFile(file);
                this.jsonFile.save();
            }
        }
    }

    @Override
    public void removeFile(FileEngine file) {
        File type = file.getFilePath().toFile();

        if (type.exists()) {
            type.delete();
        }
    }

    @Override
    public File getFile(FileEngine file) {
        return file.getFile();
    }
}