package com.ryderbelserion.ruby.other.config.types.file;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ryderbelserion.ruby.minecraft.RubyImpl;
import com.ryderbelserion.ruby.other.config.FileEngine;
import com.ryderbelserion.ruby.other.registry.RubyProvider;
import java.io.*;
import java.lang.reflect.Modifier;
import java.nio.charset.StandardCharsets;

public class JsonFile {

    private final RubyImpl ruby = RubyProvider.get();

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
            switch (ruby.platform()) {
                case PAPER, SPIGOT, FABRIC -> {
                    this.ruby.logger().error("Failed to save " + this.file.getName());
                    this.ruby.logger().error("Reason: " + exception.getMessage());
                }
                case OTHER -> {
                    System.out.println(this.ruby.prefix() + "Failed to save " + this.file.getName());
                    System.out.println(this.ruby.prefix() + "Reason: " + exception.getMessage());
                }
            }
        }

        try (InputStreamReader reader = new InputStreamReader(new FileInputStream(this.file), StandardCharsets.UTF_8)) {
            this.gson.fromJson(reader, this.context.getClass());
        } catch (Exception exception) {
            switch (ruby.platform()) {
                case PAPER, SPIGOT, FABRIC -> {
                    this.ruby.logger().error("Failed to convert " + this.file.getName());
                    this.ruby.logger().error("Reason: " + exception.getMessage());
                }
                case OTHER -> {
                    System.out.println(this.ruby.prefix() + "Failed to convert " + this.file.getName());
                    System.out.println(this.ruby.prefix() + "Reason: " + exception.getMessage());
                }
            }
        }

        this.context.load();
    }

    public void save() {
        try {
            if (!this.file.exists()) this.file.createNewFile();

            write();
        } catch (Exception exception) {
            switch (ruby.platform()) {
                case PAPER, SPIGOT, FABRIC -> {
                    this.ruby.logger().error("Failed to create or write to " + this.file.getName());
                    this.ruby.logger().error("Reason: " + exception.getMessage());
                }
                case OTHER -> {
                    System.out.println(this.ruby.prefix() + "Failed to create or write to " + this.file.getName());
                    System.out.println(this.ruby.prefix() + "Reason: " + exception.getMessage());
                }
            }
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