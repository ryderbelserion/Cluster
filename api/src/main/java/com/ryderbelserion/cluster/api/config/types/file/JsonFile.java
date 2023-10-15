package com.ryderbelserion.cluster.api.config.types.file;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ryderbelserion.cluster.api.PluginService;
import com.ryderbelserion.cluster.api.config.FileEngine;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Modifier;
import java.util.logging.Level;

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
            file.createNewFile();
        } catch (IOException exception) {
            PluginService.get().getLogger().log(Level.SEVERE, "Failed to create " + file.getName(), exception);
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