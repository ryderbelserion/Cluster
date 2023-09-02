package com.ryderbelserion.cluster.plugin;

import org.bukkit.plugin.java.JavaPlugin;

public class Example extends JavaPlugin {

    @Override
    public void onEnable() {
        super.onEnable();

        getServer().getPluginManager().registerEvents(new ExampleListener(), this);
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }
}