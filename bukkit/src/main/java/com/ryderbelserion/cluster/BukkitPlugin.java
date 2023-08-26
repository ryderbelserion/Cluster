package com.ryderbelserion.cluster;

import org.bukkit.plugin.java.JavaPlugin;

public class BukkitPlugin {

    private final JavaPlugin plugin;

    public BukkitPlugin(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public void enable() {

    }

    public void disable() {

    }

    public JavaPlugin getPlugin() {
        return this.plugin;
    }
}