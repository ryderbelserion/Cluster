package com.ryderbelserion.cluster;

import com.ryderbelserion.cluster.command.ReloadCommand;
import com.ryderbelserion.cluster.config.ConfigManager;
import com.ryderbelserion.cluster.paper.AbstractPaperPlugin;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class TestPlugin extends JavaPlugin {

    private AbstractPaperPlugin plugin;

    @Override
    public void onEnable() {
        this.plugin = new AbstractPaperPlugin(this, true);
        this.plugin.enable();

        this.plugin.getFileManager()
                .addStaticFile("config.yml")
                .addDynamicFile("crates", "CrateExample.yml")
                .addDynamicFile("schematics", "classic.nbt")
                .addDynamicFile("schematics", "nether.nbt")
                .addDynamicFile("schematics", "outdoors.nbt")
                .addDynamicFile("schematics", "sea.nbt")
                .addDynamicFile("schematics", "soul.nbt")
                .addDynamicFile("schematics", "wooden.nbt")
                .addFolder("crates")
                .addFolder("schematics")
                .create();

        ConfigManager configManager = new ConfigManager(getDataFolder().toPath(), this);

        configManager.load();

        configManager.addValue("crates", "crates_one");
        configManager.addSubValue("crates", "crates_two");

        configManager.reload();

        FileConfiguration config = this.plugin.getFileManager().getStaticFile("config.yml");

        boolean option = config.getBoolean("settings.test-option");

        this.plugin.getLogger().warning("Option: " + option);

        if (option) {
            this.plugin.getLogger().warning("Enabled.");

            this.plugin.getLogger().warning("Text: " + config.getString("settings.test-string"));
        } else {
            this.plugin.getLogger().warning("Not enabled.");
        }

        getServer().getCommandMap().register("test", new ReloadCommand(this));
    }

    @Override
    public void onDisable() {
        this.plugin.disable();
    }

    public AbstractPaperPlugin getPlugin() {
        return this.plugin;
    }
}