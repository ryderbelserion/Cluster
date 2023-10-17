package com.ryderbelserion.cluster.enums;

import org.bukkit.plugin.java.JavaPlugin;

public enum PluginSupport {

    oraxen("Oraxen"),
    items_adder("ItemsAdder"),
    head_database("HeadDatabase");

    private final String pluginName;

    PluginSupport(String pluginName) {
        this.pluginName = pluginName;
    }

    public String getPluginName() {
        return this.pluginName;
    }

    public boolean isPluginEnabled(JavaPlugin plugin) {
        return plugin.getServer().getPluginManager().isPluginEnabled(this.pluginName);
    }
}