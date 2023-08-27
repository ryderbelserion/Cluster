package com.ryderbelserion.cluster.bukkit.api.config.types;

public enum FileType {

    JSON("JSON"),
    YAML("YAML");

    private final String name;

    FileType(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name.toLowerCase();
    }
}