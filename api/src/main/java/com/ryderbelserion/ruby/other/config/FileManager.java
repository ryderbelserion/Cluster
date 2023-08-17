package com.ryderbelserion.ruby.other.config;

import com.ryderbelserion.ruby.minecraft.RubyImpl;
import com.ryderbelserion.ruby.other.config.types.file.JsonFile;
import com.ryderbelserion.ruby.other.registry.RubyProvider;
import java.io.File;

public class FileManager implements FileContext {

    private final RubyImpl ruby = RubyProvider.get();

    private JsonFile jsonFile;

    @Override
    public void addFile(FileEngine file) {
        switch (file.getFileType()) {
            case JSON -> {
                this.jsonFile = new JsonFile(file);
                this.jsonFile.load();
            }

            case YAML -> {
                switch (this.ruby.getPlatform()) {
                    case PAPER, FABRIC, SPIGOT -> this.ruby.getLogger().info(file.getFileType().getName() + " is not supported yet.");
                    case OTHER -> System.out.println(this.ruby.getPrefix() + file.getFileType().getName() + " is not supported yet.");
                }
            }
        }
    }

    @Override
    public void saveFile(FileEngine file) {
        switch (file.getFileType()) {
            case JSON -> {
                this.jsonFile = new JsonFile(file);
                this.jsonFile.save();
            }

            case YAML -> {
                switch (this.ruby.getPlatform()) {
                    case PAPER, FABRIC, SPIGOT -> this.ruby.getLogger().info(file.getFileType().getName() + " is not supported yet.");
                    case OTHER -> System.out.println(this.ruby.getPrefix() + file.getFileType().getName() + " is not supported yet.");
                }
            }
        }
    }

    @Override
    public void removeFile(FileEngine file) {
        File type = file.getFilePath().toFile();

        if (type.exists()) type.delete();
    }

    @Override
    public File getFile(FileEngine file) {
        return file.getNewFile();
    }
}