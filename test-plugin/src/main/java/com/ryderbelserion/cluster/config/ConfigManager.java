package com.ryderbelserion.cluster.config;

import com.ryderbelserion.cluster.TestPlugin;
import com.ryderbelserion.cluster.config.persist.Items;

import java.nio.file.Path;

public non-sealed class ConfigManager extends ConfigData {

    private final Path path;
    private final TestPlugin plugin;

    public ConfigManager(Path path, TestPlugin plugin) {
        super(path);

        this.path = path;

        this.plugin = plugin;
    }

    public void load() {
        this.plugin.getPlugin().getStorageManager().addFile(new Items(this.path));
    }

    public void save() {
        this.plugin.getPlugin().getStorageManager().saveFile(new Items(this.path));
    }

    public void reload() {
        save();
    }

    public void addValue(String value, String other) {
        if (hasValue(value)) return;

        commands.put(value, other);
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
}