package com.ryderbelserion.cluster.plugin;

import com.ryderbelserion.cluster.BukkitPlugin;
import org.bukkit.plugin.java.JavaPlugin;

public class Cluster extends JavaPlugin {

    private BukkitPlugin bukkitPlugin;

    @Override
    public void onEnable() {
        this.bukkitPlugin = new BukkitPlugin(this);
        this.bukkitPlugin.enable();
    }

    @Override
    public void onDisable() {
        this.bukkitPlugin.disable();
    }
}