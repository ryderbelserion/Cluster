package com.ryderbelserion.cluster;

import com.ryderbelserion.cluster.api.enums.PluginSupport;
import com.ryderbelserion.cluster.api.files.FileManager;
import com.ryderbelserion.cluster.platform.ClusterServer;
import me.arcaniax.hdb.api.HeadDatabaseAPI;
import org.bukkit.plugin.java.JavaPlugin;
import java.io.File;
import java.util.logging.Logger;

public class ClusterPackage implements ClusterServer {

    private final boolean isOraxenEnabled, isPapiEnabled, isItemsAdderEnabled;
    private final FileManager fileManager;
    private final JavaPlugin plugin;
    private final boolean isLogging;

    private boolean isHeadDatabaseEnabled;
    private HeadDatabaseAPI api;

    public ClusterPackage(JavaPlugin plugin, boolean isLogging) {
        this.isLogging = isLogging;

        this.plugin = plugin;

        boolean isEnabled = PluginSupport.headdatabase.isPluginEnabled(plugin);

        if (isEnabled) {
            this.isHeadDatabaseEnabled = true;

            this.api = new HeadDatabaseAPI();
        }

        this.isPapiEnabled = PluginSupport.placeholderapi.isPluginEnabled(plugin);

        this.isOraxenEnabled = PluginSupport.oraxen.isPluginEnabled(plugin);
        this.isItemsAdderEnabled = PluginSupport.items_adder.isPluginEnabled(plugin);

        this.fileManager = new FileManager();
    }

    @Override
    public boolean isLogging() {
        return this.isLogging;
    }

    @Override
    public boolean isPapiEnabled() {
        return this.isPapiEnabled;
    }

    @Override
    public boolean isOraxenEnabled() {
        return this.isOraxenEnabled;
    }

    @Override
    public boolean isItemsAdderEnabled() {
        return this.isItemsAdderEnabled;
    }

    @Override
    public void saveResource(String resourcePath, boolean replace) {
        this.plugin.saveResource(resourcePath, replace);
    }

    @Override
    public Logger getLogger() {
        return this.plugin.getLogger();
    }

    @Override
    public File getFolder() {
        return this.plugin.getDataFolder();
    }

    @Override
    public boolean isHeadDatabaseEnabled() {
        return this.isHeadDatabaseEnabled;
    }

    public FileManager getFileManager() {
        return this.fileManager;
    }

    public HeadDatabaseAPI getHeadDatabaseAPI() {
        if (isHeadDatabaseEnabled()) {
            getLogger().warning("HeadDatabase is not enabled, Custom Skulls cannot be used.");

            return null;
        }

        return this.api;
    }
}