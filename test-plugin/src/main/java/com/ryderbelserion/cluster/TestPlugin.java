package com.ryderbelserion.cluster;

import com.ryderbelserion.cluster.config.ConfigManager;
import com.ryderbelserion.cluster.paper.AbstractPaperPlugin;
import org.bukkit.plugin.java.JavaPlugin;

public class TestPlugin extends JavaPlugin {

    private AbstractPaperPlugin plugin;

    @Override
    public void onEnable() {
        this.plugin = new AbstractPaperPlugin(this);
        this.plugin.enable();

        ConfigManager configManager = new ConfigManager(getDataFolder().toPath(), this);

        configManager.load();

        configManager.addValue("crates", "crates_one");
        configManager.addSubValue("crates", "crates_two");

        configManager.reload();
    }

    @Override
    public void onDisable() {
        this.plugin.disable();
    }

    public AbstractPaperPlugin getPlugin() {
        return this.plugin;
    }
}