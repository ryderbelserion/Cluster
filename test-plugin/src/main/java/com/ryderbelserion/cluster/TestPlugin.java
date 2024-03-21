package com.ryderbelserion.cluster;

import org.bukkit.plugin.java.JavaPlugin;

public class TestPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        new ClusterFactory(this).copyFile(getDataFolder().toPath(), "test", "config.yml");
    }
}