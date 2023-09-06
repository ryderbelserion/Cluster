package com.ryderbelserion.cluster.plugin;

import com.ryderbelserion.cluster.api.adventure.FancyLogger;
import com.ryderbelserion.cluster.bukkit.BukkitPlugin;
import org.bukkit.plugin.java.JavaPlugin;

public class Cluster extends JavaPlugin {

    private BukkitPlugin bukkitPlugin;

    @Override
    public void onEnable() {
        this.bukkitPlugin = new BukkitPlugin(this);
        this.bukkitPlugin.enable();

        FancyLogger.success("Cluster is ready for use!");
    }

    @Override
    public void onDisable() {
        this.bukkitPlugin.disable();

        FancyLogger.success("Cluster is shutting down.");
    }
}