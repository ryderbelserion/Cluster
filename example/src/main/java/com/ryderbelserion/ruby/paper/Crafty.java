package com.ryderbelserion.ruby.paper;

import org.bukkit.plugin.java.JavaPlugin;

public class Crafty extends JavaPlugin {

    private PaperPlugin paperPlugin;

    @Override
    public void onEnable() {
        // This must go first!
        this.paperPlugin = new PaperPlugin(this);
        this.paperPlugin.enable(false);

        this.paperPlugin.fancyLogger().debug("Guten Tag!");
    }

    @Override
    public void onDisable() {
        this.paperPlugin.fancyLogger().debug("Gute Nacht!");

        // This must go last!
        this.paperPlugin.disable();
    }
}