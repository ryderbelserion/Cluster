package com.ryderbelserion.cluster;

import com.ryderbelserion.cluster.command.BaseCommand;
import com.ryderbelserion.cluster.config.ConfigManager;
import com.ryderbelserion.cluster.listeners.StructureInteractEvent;
import com.ryderbelserion.cluster.paper.AbstractPaperPlugin;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.ArrayList;

public class TestPlugin extends JavaPlugin {

    private AbstractPaperPlugin plugin;

    private ArrayList<Location> blocks;

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

        this.blocks = new ArrayList<>();

        ConfigManager configManager = new ConfigManager(getDataFolder().toPath(), this);

        configManager.load();

        configManager.addValue("crates", "crates_one");
        configManager.addSubValue("crates", "crates_two");

        configManager.reload();

        getServer().getCommandMap().register("test", new BaseCommand(this));

        getServer().getPluginManager().registerEvents(new StructureInteractEvent(this), this);
    }

    @Override
    public void onDisable() {
        this.plugin.disable();
    }

    public AbstractPaperPlugin getPlugin() {
        return this.plugin;
    }

    public ArrayList<Location> getBlocks() {
        return this.blocks;
    }
}