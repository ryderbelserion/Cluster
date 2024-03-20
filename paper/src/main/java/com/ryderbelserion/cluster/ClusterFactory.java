package com.ryderbelserion.cluster;

import com.ryderbelserion.cluster.api.enums.PluginSupport;
import me.arcaniax.hdb.api.HeadDatabaseAPI;
import org.bukkit.plugin.java.JavaPlugin;
import java.io.File;
import java.util.logging.Logger;

public class ClusterFactory extends Cluster {

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

        if (PluginSupport.headdatabase.isPluginEnabled(plugin)) {
            this.api = new HeadDatabaseAPI();
        }

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
     * @return true or false.
     */
    @Override
    public boolean isPapiEnabled() {
        return PluginSupport.placeholderapi.isPluginEnabled(this.plugin);
    }

    /**
     * @return true or false.
     */
    @Override
    public boolean isOraxenEnabled() {
        return PluginSupport.oraxen.isPluginEnabled(this.plugin);
    }

    /**
     * @return true or false.
     */
    @Override
    public boolean isItemsAdderEnabled() {
        return PluginSupport.items_adder.isPluginEnabled(this.plugin);
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
     * @return true or false
     */
    @Override
    public boolean isHeadDatabaseEnabled() {
        return PluginSupport.headdatabase.isPluginEnabled(plugin);
    }

    /**
     * @return the plugin instance
     */
    public JavaPlugin getPlugin() {
        return this.plugin;
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