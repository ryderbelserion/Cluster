package com.ryderbelserion.cluster.config;

import com.ryderbelserion.cluster.TestPlugin;
import java.nio.file.Path;
import java.util.ArrayList;

public non-sealed class ConfigManager extends ConfigData {

    private final TestPlugin plugin;

    private final ConfigData configData;

    public ConfigManager(Path path, TestPlugin plugin) {
        super(path);

        this.plugin = plugin;

        this.configData = this;
    }

    public void load() {
        this.plugin.getPlugin().getStorageFactory().addFile(this.configData);
    }

    public void save() {
        this.plugin.getPlugin().getStorageFactory().saveFile(this.configData);
    }

    public void addValue(String value, String subValue) {
        if (hasValue(value)) return;

        commands.put(value, new ArrayList<>());
        commands.get(value).add(subValue);
    }

    public void addSubValue(String value, String subValue) {
        if (!hasValue(value)) {
            addValue(value, subValue);
            return;
        }

        commands.get(value).add(subValue);
    }

    public boolean hasValue(String value) {
        return commands.containsKey(value);
    }
}