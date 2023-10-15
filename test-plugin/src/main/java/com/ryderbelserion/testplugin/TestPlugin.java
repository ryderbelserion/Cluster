package com.ryderbelserion.testplugin;

import org.bukkit.plugin.java.JavaPlugin;

public class TestPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        getLogger().info("Starting up...");
    }

    @Override
    public void onDisable() {
        getLogger().info("Shutting down...");
    }
}