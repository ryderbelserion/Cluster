package com.ryderbelserion.ruby.config.types.file;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ryderbelserion.ruby.RubyPlugin;
import com.ryderbelserion.ruby.config.FileEngine;
import com.ryderbelserion.ruby.registry.RubyProvider;
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

        this.file = this.context.getFile();

        if (this.context.getGson() != null) {
            this.gson = this.context.getGson().create();
            return;
        }

        GsonBuilder builder = new GsonBuilder().disableHtmlEscaping()
                .enableComplexMapKeySerialization()
                .excludeFieldsWithModifiers(Modifier.TRANSIENT)
                .excludeFieldsWithoutExposeAnnotation();

        this.gson = builder.create();
    }

    public void load() {
        try {
            this.file.createNewFile();
        } catch (Exception exception) {
            this.plugin.getFancyLogger().error("Failed to create " + this.file.getName());
            this.plugin.getFancyLogger().error("Reason: " + exception.getMessage());
        }

        try (InputStreamReader reader = new InputStreamReader(new FileInputStream(this.file), StandardCharsets.UTF_8)) {
            this.gson.fromJson(reader, this.context.getClass());
        } catch (Exception exception) {
            this.plugin.getFancyLogger().error("Failed to load " + this.file.getName());
            this.plugin.getFancyLogger().error("Reason: " + exception.getMessage());
        }
    }

    public void save() {
        try {
            this.file.createNewFile();

            write();
        } catch (Exception exception) {
            this.plugin.getFancyLogger().error("Failed to write/save to " + this.file.getName());
            this.plugin.getFancyLogger().error("Reason: " + exception.getMessage());
        }
    }

    private void write() throws IOException {
        try (OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(this.file), StandardCharsets.UTF_8)) {
            String values = this.gson.toJson(this.context, this.context.getClass());

            writer.write(values);
        }
    }
}