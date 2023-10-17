package com.ryderbelserion.cluster.enums;

public enum Files {

    config("config.yml");

    private final String fileName;

    Files(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return this.fileName;
    }
}