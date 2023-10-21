package com.ryderbelserion.cluster.config;

import com.google.gson.annotations.Expose;
import com.ryderbelserion.cluster.api.config.context.FileData;
import com.ryderbelserion.cluster.api.config.context.FileType;
import java.nio.file.Path;
import java.util.concurrent.ConcurrentHashMap;

public sealed class ConfigData extends FileData permits ConfigManager {

    public ConfigData(Path path) {
        super(FileType.json,"data.json", path.toString(), true);
    }

    @Expose
    public static ConcurrentHashMap<String, String> commands = new ConcurrentHashMap<>();

    @Expose
    public static String testString = "Hello";
}