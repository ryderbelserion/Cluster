package com.ryderbelserion.cluster.bukkit;

import com.ryderbelserion.cluster.api.RootPlugin;
import com.ryderbelserion.cluster.bukkit.api.registry.BukkitRegistry;
import org.bukkit.plugin.java.JavaPlugin;
import java.nio.file.Path;

public class BukkitPlugin extends RootPlugin {

    private final JavaPlugin plugin;

    public BukkitPlugin(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public void enable() {
        super.enable(this.plugin.getServer().getConsoleSender(), this.plugin.getName());

        BukkitRegistry.start(this);
    }

    public void disable() {
        super.disable();

        // This must go last.
        BukkitRegistry.stop();
    }

    public JavaPlugin getPlugin() {
        return this.plugin;
    }

    @Override
    public Path getFolder() {
        return getPlugin().getDataFolder().toPath();
    }
}