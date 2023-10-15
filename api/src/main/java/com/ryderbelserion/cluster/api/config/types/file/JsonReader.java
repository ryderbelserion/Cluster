package com.ryderbelserion.cluster.api.config.types.file;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.ryderbelserion.cluster.api.PluginService;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;

public class JsonReader {

    private final File file;
    private final Gson gson;
    private final Object context;

    public JsonReader(File file, Gson gson, Object context) {
        this.file = file;
        this.gson = gson;
        this.context = context;
    }

    public void readFile() {
        try (InputStreamReader reader = new InputStreamReader(new FileInputStream(this.file), StandardCharsets.UTF_8)) {
            this.gson.fromJson(reader, this.context.getClass());
        } catch (IOException exception) {
            PluginService.get().getLogger().log(Level.SEVERE, "Failed to read " + this.file.getName(), exception);
        } catch (JsonSyntaxException exception) {
            PluginService.get().getLogger().log(Level.SEVERE, "Failed to parse json from " + this.file.getName(), exception);
        }
    }

    public void saveFile() {
        try {
            try (OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(this.file), StandardCharsets.UTF_8)) {
                String values = this.gson.toJson(this.context, this.context.getClass());
                writer.write(values);
            }
        } catch (IOException exception) {
            PluginService.get().getLogger().log(Level.SEVERE, "Failed to write to " + this.file.getName(), exception);
        }
    }
}