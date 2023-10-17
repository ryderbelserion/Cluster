package com.ryderbelserion.cluster.api.config.context;

public enum FileType {

    json("json"),
    other("other");

    private final String name;

    FileType(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name.toLowerCase();
    }
}