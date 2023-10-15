package com.ryderbelserion.testplugin;

import com.ryderbelserion.cluster.paper.AbstractPaperPlugin;
import org.bukkit.plugin.java.JavaPlugin;

public class TestPlugin extends JavaPlugin {

    private AbstractPaperPlugin plugin;

    @Override
    public void onEnable() {
        this.plugin = new AbstractPaperPlugin(this);
        this.plugin.enable();
    }

    @Override
    public void onDisable() {
        this.plugin.disable();
    }
}