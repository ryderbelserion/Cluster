package com.ryderbelserion.cluster.api.config.types.file;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ryderbelserion.cluster.api.adventure.FancyLogger;
import com.ryderbelserion.cluster.api.config.FileEngine;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Modifier;

public class JsonFile {

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
            if (this.file.createNewFile()) FancyLogger.debug("Created new file: " + this.file.getName());
        } catch (Exception exception) {
            FancyLogger.error("Failed to create " + this.file.getName());
            FancyLogger.debug("Reason: " + exception.getMessage());
        }

        try (InputStreamReader reader = new InputStreamReader(new FileInputStream(this.file), StandardCharsets.UTF_8)) {
            this.gson.fromJson(reader, this.context.getClass());
        } catch (Exception exception) {
            FancyLogger.error("Failed to load " + this.file.getName());
            FancyLogger.debug("Reason: " + exception.getMessage());
        }
    }

    public void save() {
        try {
            if (this.file.createNewFile()) FancyLogger.debug("Created new file: " + this.file.getName());

            write();
        } catch (Exception exception) {
            FancyLogger.error("Failed to write/save to " + this.file.getName());
            FancyLogger.debug("Reason: " + exception.getMessage());
        }
    }

    private void write() throws IOException {
        try (OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(this.file), StandardCharsets.UTF_8)) {
            String values = this.gson.toJson(this.context, this.context.getClass());

            writer.write(values);
        }
    }
}