package com.ryderbelserion.cluster.api.config;

import com.ryderbelserion.cluster.api.adventure.FancyLogger;
import com.ryderbelserion.cluster.api.config.types.file.JsonFile;
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

            case YAML -> FancyLogger.info(file.getFileType().getName() + " is not supported yet.");
        }
    }

    @Override
    public void saveFile(FileEngine file) {
        switch (file.getFileType()) {
            case JSON -> {
                this.jsonFile = new JsonFile(file);
                this.jsonFile.save();
            }

            case YAML -> FancyLogger.info(file.getFileType().getName() + " is not supported yet.");
        }
    }

    @Override
    public void removeFile(FileEngine file) {
        File type = file.getFilePath().toFile();

        if (type.exists()) if (type.delete()) FancyLogger.debug("Deleted file " + type.getName());
    }

    @Override
    public File getFile(FileEngine file) {
        return file.getFile();
    }
}