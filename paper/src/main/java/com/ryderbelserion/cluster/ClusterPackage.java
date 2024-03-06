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

    private boolean isLogging;

    private boolean isHeadDatabaseEnabled;
    private HeadDatabaseAPI api;

    /**
     * Initializes the framework.
     *
     * @param plugin the instance of the plugin using the framework.
     */
    public ClusterPackage(JavaPlugin plugin) {
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

    /**
     * Sets whether we log or not.
     *
     * @param value true or false.
     */
    public void setLogging(boolean value) {
        this.isLogging = value;
    }

    /**
     * @return true or false.
     */
    @Override
    public boolean isLogging() {
        return this.isLogging;
    }

    /**
     * @return true or false.
     */
    @Override
    public boolean isPapiEnabled() {
        return this.isPapiEnabled;
    }

    /**
     * @return true or false.
     */
    @Override
    public boolean isOraxenEnabled() {
        return this.isOraxenEnabled;
    }

    /**
     * @return true or false.
     */
    @Override
    public boolean isItemsAdderEnabled() {
        return this.isItemsAdderEnabled;
    }

    /**
     * Saves the raw contents of any resource embedded with a plugin's .jar if it can be found.
     *
     * @param resourcePath the embedded resource path to look for within the
     *     plugin's .jar file. (No preceding slash).
     * @param replace if true, the embedded resource will overwrite the
     *     contents of an existing file.
     * @throws IllegalArgumentException if the resource path is null, empty,
     *     or points to a nonexistent resource.
     */
    @Override
    public void saveResource(String resourcePath, boolean replace) {
        this.plugin.saveResource(resourcePath, replace);
    }

    /**
     * @return the plugin logger.
     */
    @Override
    public Logger getLogger() {
        return this.plugin.getLogger();
    }

    /**
     * @return the plugin datafolder.
     */
    @Override
    public File getFolder() {
        return this.plugin.getDataFolder();
    }

    /**
     * @return true or false
     */
    @Override
    public boolean isHeadDatabaseEnabled() {
        return this.isHeadDatabaseEnabled;
    }

    /**
     * @return instance of the FileManager.
     */
    public FileManager getFileManager() {
        return this.fileManager;
    }

    /**
     * @return instance of HeadDatabaseAPI.
     */
    public HeadDatabaseAPI getHeadDatabaseAPI() {
        if (isHeadDatabaseEnabled()) {
            getLogger().warning("HeadDatabase is not enabled, Custom Skulls cannot be used.");

            return null;
        }

        return this.api;
    }
}