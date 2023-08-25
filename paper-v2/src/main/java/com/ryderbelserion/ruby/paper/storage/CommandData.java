package com.ryderbelserion.ruby.paper.storage;

import com.google.gson.annotations.Expose;
import com.ryderbelserion.ruby.other.config.FileEngine;
import com.ryderbelserion.ruby.other.config.types.FileType;
import com.ryderbelserion.ruby.paper.storage.objects.SubCommandData;

import java.nio.file.Path;
import java.util.concurrent.ConcurrentHashMap;

public sealed class CommandData extends FileEngine permits DataManager {

    public CommandData(Path path) {
        super("commands.json", path, FileType.JSON);
    }

    @Expose
    protected static ConcurrentHashMap<String, SubCommandData> commands = new ConcurrentHashMap<>();
}