package com.ryderbelserion.cluster.paper;

import com.ryderbelserion.cluster.api.AbstractPlugin;
import com.ryderbelserion.cluster.paper.files.FileManager;
import me.arcaniax.hdb.api.HeadDatabaseAPI;
import net.kyori.adventure.audience.Audience;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabCompleter;
import org.bukkit.plugin.java.JavaPlugin;
import java.io.File;
import java.util.logging.Logger;

public class ClusterFactory extends AbstractPlugin {

    private final boolean isLogging;

    private HeadDatabaseAPI databaseAPI;

    private FileManager fileManager;
    private JavaPlugin plugin;

    public ClusterFactory(JavaPlugin plugin, boolean isLogging) {
        this.plugin = plugin;

        this.isLogging = isLogging;

        this.fileManager = new FileManager(this, this.plugin);
    }

    public ClusterFactory(boolean isLogging) {
        this.isLogging = isLogging;
    }

    @Override
    public void enable() {
        super.enable();

        if (!this.plugin.getDataFolder().exists()) {
            this.plugin.getDataFolder().mkdirs();
        }

        ClusterService.setService(this);
    }

    @Override
    public void disable() {
        super.disable();

        // This must go last.
        ClusterService.stopService();
    }

    @Override
    public boolean isLogging() {
        return this.isLogging;
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

        this.fileManager = new FileManager(this, this.plugin);
    }

    public void setDatabaseAPI(HeadDatabaseAPI databaseAPI) {
        this.databaseAPI = databaseAPI;
    }

    public HeadDatabaseAPI getDatabaseAPI() {
        try {
            return this.databaseAPI;
        } catch (Exception exception) {
            return new HeadDatabaseAPI();
        }
    }

    public FileManager getFileManager() {
        return this.fileManager;
    }

    public JavaPlugin getPlugin() {
        return this.plugin;
    }
}