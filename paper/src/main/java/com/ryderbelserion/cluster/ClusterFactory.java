package com.ryderbelserion.cluster;

import com.ryderbelserion.cluster.api.enums.PluginSupport;
import me.arcaniax.hdb.api.HeadDatabaseAPI;
import org.bukkit.plugin.java.JavaPlugin;
import java.io.File;
import java.util.logging.Logger;

public class ClusterFactory extends Cluster {

    private final boolean isOraxenEnabled, isPapiEnabled, isItemsAdderEnabled, isHeadDatabaseEnabled;
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

        this.isHeadDatabaseEnabled = PluginSupport.headdatabase.isPluginEnabled(plugin);

        if (this.isHeadDatabaseEnabled) {
            this.api = new HeadDatabaseAPI();
        }

        this.isItemsAdderEnabled = PluginSupport.items_adder.isPluginEnabled(plugin);
        this.isOraxenEnabled = PluginSupport.oraxen.isPluginEnabled(plugin);
        this.isPapiEnabled = PluginSupport.placeholderapi.isPluginEnabled(plugin);

        this.plugin = plugin;
    }

    /**
     * @return true or false.
     */
    public boolean isLogging() {
        return this.isLogging;
    }

    /**
     * @return true or false.
     */
    public boolean isPapiEnabled() {
        return this.isPapiEnabled;
    }

    /**
     * @return true or false.
     */
    public boolean isOraxenEnabled() {
        return this.isOraxenEnabled;
    }

    /**
     * @return true or false.
     */
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
    public void saveResource(String resourcePath, boolean replace) {
        this.plugin.saveResource(resourcePath, replace);
    }

    /**
     * Sets whether we log or not.
     *
     * @param isLogging true or false.
     */
    public void setLogging(boolean isLogging) {
        this.isLogging = isLogging;
    }

    /**
     * @return the plugin logger.
     */
    public Logger getLogger() {
        return this.plugin.getLogger();
    }

    /**
     * @return the plugin datafolder.
     */
    public File getFolder() {
        return this.plugin.getDataFolder();
    }

    /**
     * @return true or false
     */
    public boolean isHeadDatabaseEnabled() {
        return this.isHeadDatabaseEnabled;
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