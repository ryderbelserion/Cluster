package com.ryderbelserion.ruby.other.config;

import com.google.gson.GsonBuilder;
import com.ryderbelserion.ruby.minecraft.RubyImpl;
import com.ryderbelserion.ruby.other.config.types.FileType;
import com.ryderbelserion.ruby.other.registry.RubyProvider;

import java.io.File;
import java.nio.file.Path;

public abstract class FileEngine {

    private final RubyImpl ruby = RubyProvider.get();

    private final String fileName;
    private final Path filePath;
    private final FileType fileType;

    private GsonBuilder gson;

    public FileEngine(String fileName, Path filePath, FileType fileType) {
        this.fileName = fileName;
        this.filePath = filePath;
        this.fileType = fileType;
    }

    public abstract void load();

    public abstract void save();

    public void setGsonBuilder(GsonBuilder gson) {
        if (this.fileType != FileType.JSON) {
            switch (ruby.getPlatform()) {
                case PAPER, SPIGOT, FABRIC -> this.ruby.getLogger().error("You cannot use json if the file type isn't " + FileType.JSON.getName());
                case OTHER -> System.out.println(this.ruby.getPrefix() + "You cannot use json if the file type isn't " + FileType.JSON.getName());
            }

            return;
        }

        this.gson = gson;
    }

    public FileType getFileType() {
        return this.fileType;
    }

    public GsonBuilder getGson() {
        return this.gson;
    }

    public Path getFilePath() {
        return this.filePath;
    }

    public File getNewFile() {
        return this.filePath.resolve(this.fileName).toFile();
    }

    public String getFileName() {
        return this.fileName;
    }
}