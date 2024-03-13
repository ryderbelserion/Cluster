package com.ryderbelserion.cluster.platform;

import com.ryderbelserion.cluster.api.enums.PluginSupport;
import me.arcaniax.hdb.api.HeadDatabaseAPI;
import org.bukkit.plugin.java.JavaPlugin;
import java.io.File;
import java.util.logging.Logger;

public class ClusterPaperServer implements ClusterServer {

    private final boolean isOraxenEnabled, isPapiEnabled, isItemsAdderEnabled, isHeadDatabaseEnabled;
    private final JavaPlugin plugin;
    private HeadDatabaseAPI api;
    private boolean isLogging;

    public ClusterPaperServer(JavaPlugin plugin) {
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
     * Sets whether we log or not.
     *
     * @param value true or false.
     */
    @Override
    public void setLogging(boolean value) {
        this.isLogging = value;
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