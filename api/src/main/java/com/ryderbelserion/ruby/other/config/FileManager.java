package com.ryderbelserion.ruby.other.config;

import com.ryderbelserion.ruby.other.config.types.file.JsonFile;
import java.io.File;

public class FileManager implements FileContext {

    private JsonFile jsonFile;

    @Override
    public void addFile(FileEngine file) {
        switch (file.getFileType()) {
            case JSON -> {
                this.jsonFile = new JsonFile(file);
                this.jsonFile.load();
            }

            case YAML -> System.out.println("Currently not supported.");

            default -> System.out.println("Error");
        }
    }

    @Override
    public void saveFile(FileEngine file) {
        switch (file.getFileType()) {
            case JSON -> {
                this.jsonFile = new JsonFile(file);
                this.jsonFile.save();
            }

            case YAML -> System.out.println("Currently not supported.");

            default -> System.out.println("Error");
        }
    }

    @Override
    public void removeFile(FileEngine file) {
        switch (file.getFileType()) {
            case JSON -> {
                this.jsonFile = new JsonFile(file);
            }

            case YAML -> System.out.println("Currently not supported.");

            default -> System.out.println("Error");
        }
    }

    @Override
    public File getFile(FileEngine file) {
        return null;
    }
}