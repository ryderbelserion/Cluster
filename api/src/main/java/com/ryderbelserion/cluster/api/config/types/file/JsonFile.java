package com.ryderbelserion.cluster.api.config.types.file;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ryderbelserion.cluster.api.RootService;
import com.ryderbelserion.cluster.api.adventure.FancyLogger;
import com.ryderbelserion.cluster.api.config.FileEngine;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Modifier;

public class JsonFile {

    private final JsonReader jsonReader;

    public JsonFile(FileEngine context) {
        File file = context.getFile();

        GsonBuilder builder = new GsonBuilder()
                .disableHtmlEscaping()
                .enableComplexMapKeySerialization()
                .excludeFieldsWithModifiers(Modifier.TRANSIENT)
                .excludeFieldsWithoutExposeAnnotation();

        Gson gson = (context.getGson() != null) ? context.getGson().create() : builder.create();

        try {
            if (file.createNewFile()) {
                if (RootService.getService().isLegacy()) {
                    System.out.println("Created new file: " + file.getName());
                } else {
                    FancyLogger.debug("Created new file: " + file.getName());
                }
            }
        } catch (IOException exception) {
            if (RootService.getService().isLegacy()) {
                System.out.println("Failed to create " + file.getName());
                System.out.println(exception.getMessage());
            } else {
                FancyLogger.error("Failed to create " + file.getName(), exception);
            }
        }

        this.jsonReader = new JsonReader(file, gson, context);
    }

    public void load() {
        this.jsonReader.readFile();
    }

    public void save() {
        this.jsonReader.saveFile();
    }
}