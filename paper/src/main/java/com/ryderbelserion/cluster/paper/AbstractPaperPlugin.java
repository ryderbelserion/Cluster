package com.ryderbelserion.cluster.paper;

import com.ryderbelserion.cluster.api.AbstractPlugin;
import com.ryderbelserion.cluster.api.config.FileManager;
import net.kyori.adventure.audience.Audience;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabCompleter;
import org.bukkit.plugin.java.JavaPlugin;
import java.io.File;
import java.util.logging.Logger;

public class AbstractPaperPlugin extends AbstractPlugin {

    private FileManager fileManager;
    private StorageManager storageManager;
    private JavaPlugin plugin;

    public AbstractPaperPlugin(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public AbstractPaperPlugin() {}

    @Override
    public void enable() {
        super.enable();

        this.storageManager = new StorageManager();

        if (!this.plugin.getDataFolder().exists()) {
            this.plugin.getDataFolder().mkdirs();
        }
    }

    @Override
    public void disable() {
        super.disable();

        // This must go last.
        PaperService.stopService();
    }

    @Override
    public StorageManager getStorageManager() {
        return this.storageManager;
    }

    @Override
    public Logger getLogger() {
        return this.plugin.getLogger();
    }

    @Override
    public File getDataFolder() {
        return this.plugin.getDataFolder();
    }

    @Override
    public Audience getConsole() {
        return this.plugin.getServer();
    }

    public void registerCommand(PluginCommand pluginCommand, TabCompleter tabCompleter, CommandExecutor commandExecutor) {
        if (pluginCommand != null) {
            pluginCommand.setExecutor(commandExecutor);

            if (tabCompleter != null) {
                pluginCommand.setTabCompleter(tabCompleter);
            }
        }
    }

    public void setPlugin(JavaPlugin plugin) {
        // If the plugin is already registered,
        // return as we don't want it registered again.
        if (this.plugin != null) {
            this.plugin.getLogger().warning("The plugin variable is already set. You cannot override it.");

            return;
        }

        // Set the plugin variable.
        this.plugin = plugin;
    }
}