package com.ryderbelserion.ruby.other.config;

import com.google.gson.GsonBuilder;
import com.ryderbelserion.ruby.minecraft.RubyPlugin;
import com.ryderbelserion.ruby.other.config.types.FileType;
import com.ryderbelserion.ruby.minecraft.registry.RubyProvider;
import org.jetbrains.annotations.NotNull;
import java.io.File;
import java.nio.file.Path;

public abstract class FileEngine {

    private final @NotNull RubyPlugin plugin = RubyProvider.get();

    private final String fileName;
    private final Path filePath;
    private final FileType fileType;

    private GsonBuilder gson;

    public FileEngine(String fileName, Path filePath, FileType fileType) {
        this.fileName = fileName;
        this.filePath = filePath;
        this.fileType = fileType;
    }

    public void setGsonBuilder(GsonBuilder gson) {
        if (this.fileType != FileType.JSON) {
            this.plugin.getFancyLogger().error("You cannot use json if the file type isn't " + FileType.JSON.getName());

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

    public File getFile() {
        return new File(this.filePath.toFile(), this.fileName);
    }
}