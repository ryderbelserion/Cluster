package com.ryderbelserion.cluster.bukkit;

import com.ryderbelserion.cluster.api.RootPlugin;
import com.ryderbelserion.cluster.api.adventure.FancyLogger;
import com.ryderbelserion.cluster.api.config.FileManager;
import com.ryderbelserion.cluster.bukkit.registry.BukkitRegistry;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabCompleter;
import org.bukkit.plugin.java.JavaPlugin;
import java.io.File;
import java.nio.file.Path;

public class BukkitPlugin extends RootPlugin {

    private JavaPlugin plugin;
    private final Path path;
    private FileManager fileManager;

    public BukkitPlugin(JavaPlugin plugin) {
        this.plugin = plugin;

        this.path = this.plugin.getServer().getPluginsFolder().toPath().resolve("Cluster");
    }

    public void setPlugin(JavaPlugin plugin) {
        // If the plugin is already registered,
        // return as we don't want it registered again.
        if (this.plugin != null) {
            FancyLogger.warn("The plugin variable is already set. You cannot register or overwrite it.");
            return;
        }

        // Set the plugin variable.
        this.plugin = plugin;
    }

    public void enable() {
        super.enable(this.plugin.getServer().getConsoleSender());

        BukkitRegistry.start(this);

        this.fileManager = new FileManager();

        File file = this.path.toFile();

        if (!file.exists()) file.mkdirs();

        if (!getPlugin().getDataFolder().exists()) getPlugin().getDataFolder().mkdirs();
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

    @Override
    public FileManager getFileManager() {
        return this.fileManager;
    }

    public void registerCommand(PluginCommand pluginCommand, TabCompleter tabCompleter, CommandExecutor commandExecutor) {
        if (pluginCommand != null) {
            pluginCommand.setExecutor(commandExecutor);

            if (tabCompleter != null) pluginCommand.setTabCompleter(tabCompleter);
        }
    }
}