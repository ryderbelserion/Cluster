package com.ryderbelserion.ruby.config;

import com.ryderbelserion.ruby.RubyPlugin;
import com.ryderbelserion.ruby.config.types.file.JsonFile;
import com.ryderbelserion.ruby.registry.RubyProvider;
import org.jetbrains.annotations.NotNull;
import java.io.File;

public class FileManager implements FileContext {

    private final @NotNull RubyPlugin plugin = RubyProvider.get();

    private JsonFile jsonFile;

    @Override
    public void addFile(FileEngine file) {
        switch (file.getFileType()) {
            case JSON -> {
                this.jsonFile = new JsonFile(file);
                this.jsonFile.load();
            }

            case YAML -> this.plugin.getFancyLogger().info(file.getFileType().getName() + " is not supported yet.");
        }
    }

    @Override
    public void saveFile(FileEngine file) {
        switch (file.getFileType()) {
            case JSON -> {
                this.jsonFile = new JsonFile(file);
                this.jsonFile.save();
            }

            case YAML -> this.plugin.getFancyLogger().info(file.getFileType().getName() + " is not supported yet.");
        }
    }

    @Override
    public void removeFile(FileEngine file) {
        File type = file.getFilePath().toFile();

        if (type.exists()) type.delete();
    }

    @Override
    public File getFile(FileEngine file) {
        return file.getFile();
    }
}