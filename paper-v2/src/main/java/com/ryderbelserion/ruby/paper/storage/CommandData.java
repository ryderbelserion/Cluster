package com.ryderbelserion.ruby.paper.storage;

import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import com.ryderbelserion.ruby.config.FileEngine;
import com.ryderbelserion.ruby.config.types.FileType;
import com.ryderbelserion.ruby.paper.storage.objects.SubCommandData;
import java.lang.reflect.Modifier;
import java.nio.file.Path;
import java.util.concurrent.ConcurrentHashMap;

public sealed class CommandData extends FileEngine permits DataManager {

    public CommandData(Path path) {
        super("commands.json", path, FileType.JSON);

        GsonBuilder builder = new GsonBuilder()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .excludeFieldsWithoutExposeAnnotation()
                .excludeFieldsWithModifiers(Modifier.TRANSIENT);

        setGsonBuilder(builder);
    }

    @Expose
    public static ConcurrentHashMap<String, SubCommandData> commands = new ConcurrentHashMap<>();
}