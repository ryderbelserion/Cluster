package com.ryderbelserion.cluster;

import com.ryderbelserion.cluster.command.BaseCommand;
import com.ryderbelserion.cluster.listeners.StructureInteractEvent;
import com.ryderbelserion.cluster.paper.ClusterFactory;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.ArrayList;

public class TestPlugin extends JavaPlugin {

    private ClusterFactory plugin;

    private ArrayList<Location> blocks;

    @Override
    public void onEnable() {
        this.plugin = new ClusterFactory(this, true);
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

        getServer().getCommandMap().register("test", new BaseCommand(this));

        getServer().getPluginManager().registerEvents(new StructureInteractEvent(this), this);
    }

    @Override
    public void onDisable() {
        this.plugin.disable();
    }

    public ClusterFactory getPlugin() {
        return this.plugin;
    }

    public ArrayList<Location> getBlocks() {
        return this.blocks;
    }
}