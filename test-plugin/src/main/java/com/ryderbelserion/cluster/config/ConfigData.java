package com.ryderbelserion.cluster.config;

import com.google.gson.annotations.Expose;
import com.ryderbelserion.cluster.api.config.context.FileData;
import com.ryderbelserion.cluster.api.config.context.FileType;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

public sealed class ConfigData extends FileData permits ConfigManager {

    public ConfigData(Path path) {
        super(FileType.json,"data.json", path, true);
    }

    @Expose
    protected static ConcurrentHashMap<String, ArrayList<String>> commands = new ConcurrentHashMap<>();

    @Expose
    protected static String testString = "Hello";
}