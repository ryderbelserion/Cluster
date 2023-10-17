package com.ryderbelserion.cluster.api.config.context;

public enum FileType {

    json("json");

    private final String name;

    FileType(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name.toLowerCase();
    }
}