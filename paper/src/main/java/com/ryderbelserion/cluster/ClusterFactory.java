package com.ryderbelserion.cluster;

import me.arcaniax.hdb.api.HeadDatabaseAPI;
import org.bukkit.plugin.java.JavaPlugin;
import java.io.File;
import java.util.logging.Logger;

public abstract class ClusterFactory extends Cluster {

    private final JavaPlugin plugin;
    private HeadDatabaseAPI api;
    private boolean isLogging;

    /**
     * Initializes the framework.
     *
     * @param plugin the instance of the plugin using the framework.
     */
    public ClusterFactory(JavaPlugin plugin) {
        super();

        this.plugin = plugin;
    }

    /**
     * @return true or false.
     */
    @Override
    public boolean isLogging() {
        return this.isLogging;
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
     * Sets whether we log or not.
     *
     * @param isLogging true or false.
     */
    @Override
    public void setLogging(boolean isLogging) {
        this.isLogging = isLogging;
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
     * @return the plugin instance.
     */
    public JavaPlugin getPlugin() {
        return this.plugin;
    }

    /**
     * Set the instance of head database api.
     *
     * @param api instance of head database api.
     */
    public void setApi(HeadDatabaseAPI api) {
        if (this.api != null) {
            getLogger().warning("HeadDatabase is already enabled.");

            return;
        }

        this.api = api;
    }

    /**
     * @return instance of HeadDatabaseAPI.
     */
    public HeadDatabaseAPI getHeadDatabaseAPI() {
        if (!isHeadDatabaseEnabled()) {
            getLogger().warning("HeadDatabase is not enabled, Custom Skulls cannot be used.");

            return null;
        }

        return this.api;
    }
}