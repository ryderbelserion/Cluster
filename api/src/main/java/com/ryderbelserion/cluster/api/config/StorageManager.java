package com.ryderbelserion.cluster.api.config;

import com.ryderbelserion.cluster.api.PluginService;
import com.ryderbelserion.cluster.api.config.context.FileContext;
import com.ryderbelserion.cluster.api.config.context.FileData;
import com.ryderbelserion.cluster.api.config.types.json.JsonFile;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

public class StorageManager implements FileContext {

    private JsonFile jsonFile;

    @Override
    public void addFile(FileData file) {
        switch (file.getType()) {
            case json -> this.jsonFile = new JsonFile(file);

            case other -> {
                if (file.getFile().exists()) return;

                try {
                    file.getFile().createNewFile();
                } catch (IOException exception) {
                    PluginService.get().getLogger().log(Level.WARNING, "The file " + file.getFile().getName() + " already exists.", exception);
                }
            }
        }
    }

    @Override
    public void saveFile(FileData file) {
        switch (file.getType()) {
            case json -> {
                this.jsonFile = new JsonFile(file);
                this.jsonFile.read();
                this.jsonFile.write();
            }

            case other -> {}
        }
    }

    @Override
    public void removeFile(FileData file) {
        switch (file.getType()) {
            case json -> {
                this.jsonFile = new JsonFile(file);
                this.jsonFile.removeFile();
            }

            case other -> {
                if (!file.getFile().exists()) return;

                file.getFile().delete();
            }
        }
    }

    @Override
    public File getFile(FileData file) {
        return file.getFile();
    }
}