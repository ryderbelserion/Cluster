package com.ryderbelserion.cluster.bukkit;

import com.ryderbelserion.cluster.api.RootPlugin;
import com.ryderbelserion.cluster.api.adventure.FancyLogger;
import com.ryderbelserion.cluster.api.config.FileManager;
import com.ryderbelserion.cluster.api.utils.FileUtils;
import com.ryderbelserion.cluster.bukkit.registry.BukkitRegistry;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabCompleter;
import org.bukkit.plugin.java.JavaPlugin;
import java.nio.file.Path;

public class BukkitPlugin extends RootPlugin {

    private FileManager fileManager;
    private FileUtils fileUtils;
    private JavaPlugin plugin;

    public BukkitPlugin(JavaPlugin plugin) {
        this.plugin = plugin;
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

        this.fileUtils = new FileUtils();
        this.fileManager = new FileManager();

        if (!this.plugin.getDataFolder().exists()) {
            this.plugin.getDataFolder().mkdirs();
        }
    }

    public void disable() {
        super.disable();

        // This must go last.
        BukkitRegistry.stop();
    }

    @Override
    public FileManager getFileManager() {
        return this.fileManager;
    }

    @Override
    public FileUtils getFileUtils() {
        return this.fileUtils;
    }

    @Override
    public Path getFolder() {
        return this.plugin.getDataFolder().toPath();
    }

    public void registerCommand(PluginCommand pluginCommand, TabCompleter tabCompleter, CommandExecutor commandExecutor) {
        if (pluginCommand != null) {
            pluginCommand.setExecutor(commandExecutor);

            if (tabCompleter != null) {
                pluginCommand.setTabCompleter(tabCompleter);
            }
        }
    }
}