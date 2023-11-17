package com.ryderbelserion.cluster.config;

import com.ryderbelserion.cluster.TestPlugin;
import com.ryderbelserion.cluster.config.persist.Items;

import java.nio.file.Path;

public non-sealed class ConfigManager extends ConfigData {

    private final TestPlugin plugin;

    private final Items items;
    private final ConfigData configData;

    public ConfigManager(Path path, TestPlugin plugin) {
        super(path);

        this.plugin = plugin;

        this.items = new Items(path);
        this.configData = new ConfigData(path);
    }

    public void load() {
        this.plugin.getPlugin().getStorageManager().addFile(this.items);

        this.plugin.getPlugin().getStorageManager().addFile(this.configData);
    }

    public void save() {
        this.plugin.getPlugin().getStorageManager().saveFile(this.items);

        this.plugin.getPlugin().getStorageManager().saveFile(this.configData);
    }

    public void reload() {
        save();
    }

    public void addValue(String value, String subValue) {
        if (hasValue(value)) return;

        commands.put(value, subValue);
    }

    public void addSubValue(String value, String subValue) {
        if (!hasValue(value)) {
            addValue(value, subValue);
            return;
        }

        commands.put(value, subValue);
    }

    public boolean hasValue(String value) {
        return commands.containsKey(value);
    }

    public int size() {
        return commands.size();
    }
}