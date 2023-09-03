package com.ryderbelserion.cluster.plugin;

import com.ryderbelserion.cluster.api.adventure.FancyLogger;
import com.ryderbelserion.cluster.bukkit.BukkitPlugin;
import com.ryderbelserion.cluster.bukkit.items.ParentBuilder;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class Cluster extends JavaPlugin implements Listener {

    private BukkitPlugin bukkitPlugin;

    @Override
    public void onEnable() {
        this.bukkitPlugin = new BukkitPlugin(this);
        this.bukkitPlugin.enable();

        getServer().getPluginManager().registerEvents(this, this);

        FancyLogger.info("Cluster is ready for use!");
    }

    @Override
    public void onDisable() {
        this.bukkitPlugin.disable();

        FancyLogger.info("Cluster is shutting down.");
    }

    @EventHandler
    public void onInteractEvent(PlayerInteractEvent event) {
        Block block = event.getClickedBlock();

        if (block == null) return;

        ParentBuilder.of(block).setBlock(Material.GRASS_BLOCK, true).setBiome(Biome.MANGROVE_SWAMP).build();

        FancyLogger.debug("Block: " + block.getBiome().name());
    }
}