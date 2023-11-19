package com.ryderbelserion.cluster.api.config.types.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.ryderbelserion.cluster.api.PluginService;
import com.ryderbelserion.cluster.api.config.context.FileData;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Modifier;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;

public class JsonFile {

    private final FileData fileData;

    private Gson gson;
    private File file;

    public JsonFile(FileData fileData) {
        this.fileData = fileData;
    }

    public void load() {
        if (this.fileData.getGson() != null) {
            this.gson = this.fileData.getGson().create();
        } else {
            GsonBuilder builder = new GsonBuilder()
                    .disableHtmlEscaping()
                    .enableComplexMapKeySerialization()
                    .excludeFieldsWithModifiers(Modifier.TRANSIENT)
                    .excludeFieldsWithoutExposeAnnotation();

            this.gson = fileData.isData() ? builder.create() : builder.setPrettyPrinting().create();
        }

        this.file = fileData.getFile();

        if (!this.file.exists()) {
            try {
                this.file.createNewFile();

                write();
            } catch (IOException exception) {
                PluginService.get().getLogger().log(Level.SEVERE, "Failed to create " + file.getName(), exception);
            }
        } else {
            read();
        }
    }

    public void read() {
        String content = readCatch();

        if (content == null || !this.file.exists()) return;

        try (InputStreamReader reader = new InputStreamReader(new FileInputStream(this.file), StandardCharsets.UTF_8)) {
            this.gson.fromJson(reader, this.fileData.getClass());
        } catch (IOException exception) {
            PluginService.get().getLogger().log(Level.SEVERE, "Failed to read " + this.file.getName(), exception);
        } catch (JsonSyntaxException exception) {
            PluginService.get().getLogger().log(Level.SEVERE, "Failed to parse json from " + this.file.getName(), exception);
        }
    }

    public void write() {
        try (OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(this.file), StandardCharsets.UTF_8)) {
            String values = this.gson.toJson(this.fileData, this.fileData.getClass());
            writer.write(values);
        } catch (IOException exception) {
            PluginService.get().getLogger().log(Level.SEVERE, "Failed to write to " + this.file.getName(), exception);
        }
    }

    public void removeFile() {
        if (this.file.exists()) {
            this.file.delete();
        }
    }

    private String readCatch() {
        try {
            return readFile();
        } catch (IOException exception) {
            return null;
        }
    }

    private String readFile() throws IOException {
        int length = (int) this.file.length();
        byte[] output = new byte[length];
        InputStream in = new FileInputStream(this.file);
        int offset = 0;

        while (offset < length) {
            offset += in.read(output, offset, (length - offset));
        }

        in.close();

        return new String(output, StandardCharsets.UTF_8);
    }
}