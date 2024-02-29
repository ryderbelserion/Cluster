package com.ryderbelserion.cluster.api.enums;

import org.bukkit.plugin.java.JavaPlugin;

public enum PluginSupport {

    placeholderapi("PlaceholderAPI"),
    oraxen("Oraxen"),
    headdatabase("HeadDatabase"),
    items_adder("ItemsAdder");

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