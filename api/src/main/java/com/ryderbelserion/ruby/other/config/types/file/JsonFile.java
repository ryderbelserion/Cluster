package com.ryderbelserion.ruby.other.config.types.file;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ryderbelserion.ruby.minecraft.RubyPlugin;
import com.ryderbelserion.ruby.other.config.FileEngine;
import com.ryderbelserion.ruby.other.registry.RubyProvider;
import org.jetbrains.annotations.NotNull;
import java.io.*;
import java.lang.reflect.Modifier;
import java.nio.charset.StandardCharsets;

public class JsonFile {

    private final @NotNull RubyPlugin plugin = RubyProvider.get();

    private final FileEngine context;

    private final File file;
    private final Gson gson;

    public JsonFile(FileEngine context) {
        this.context = context;

        this.file = context.getNewFile();

        if (context.getGson() != null) {
            this.gson = context.getGson().create();
            return;
        }

        GsonBuilder builder = new GsonBuilder().disableHtmlEscaping()
                .excludeFieldsWithModifiers(Modifier.TRANSIENT)
                .excludeFieldsWithoutExposeAnnotation();

        this.gson = builder.create();
    }

    public void load() {
        try {
            if (this.file.createNewFile()) {
                save();

                return;
            }
        } catch (Exception exception) {
            this.plugin.getFancyLogger().error("Failed to save " + this.file.getName());
            this.plugin.getFancyLogger().error("Reason: " + exception.getMessage());
        }

        try (InputStreamReader reader = new InputStreamReader(new FileInputStream(this.file), StandardCharsets.UTF_8)) {
            this.gson.fromJson(reader, this.context.getClass());
        } catch (Exception exception) {
            this.plugin.getFancyLogger().error("Failed to convert " + this.file.getName());
            this.plugin.getFancyLogger().error("Reason: " + exception.getMessage());
        }

        this.context.load();
    }

    public void save() {
        try {
            if (!this.file.exists()) this.file.createNewFile();

            write();
        } catch (Exception exception) {
            this.plugin.getFancyLogger().error("Failed to create or write to " + this.file.getName());
            this.plugin.getFancyLogger().error("Reason: " + exception.getMessage());
        }

        this.context.save();
    }

    private void write() throws IOException {
        try (OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(this.file), StandardCharsets.UTF_8)) {
            String values = this.gson.toJson(this.context, this.context.getClass());

            writer.write(values);
        }
    }
}