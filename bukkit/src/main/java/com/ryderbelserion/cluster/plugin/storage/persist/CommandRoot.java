package com.ryderbelserion.cluster.plugin.storage.persist;

import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import com.ryderbelserion.cluster.api.config.FileEngine;
import com.ryderbelserion.cluster.api.config.types.FileType;
import com.ryderbelserion.cluster.plugin.storage.persist.objects.CommandData;
import java.lang.reflect.Modifier;
import java.nio.file.Path;
import java.util.concurrent.ConcurrentHashMap;

public sealed class CommandRoot extends FileEngine permits RootManager {


    public CommandRoot(Path path) {
        super("commands.json", path, FileType.JSON);

        GsonBuilder builder = new GsonBuilder()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .excludeFieldsWithoutExposeAnnotation()
                .excludeFieldsWithModifiers(Modifier.TRANSIENT);

        setGsonBuilder(builder);
    }

    @Expose
    public static ConcurrentHashMap<String, CommandData> commands = new ConcurrentHashMap<>();
}