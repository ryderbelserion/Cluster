package com.ryderbelserion.cluster.api;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

public class PluginService {

    private static ClusterPlugin plugin = null;

    public static @NotNull ClusterPlugin get() {
        ClusterPlugin instance = PluginService.plugin;

        if (instance == null) {
            throw new RuntimeException("The API has not been properly initialized!");
        }

        return plugin;
    }

    @ApiStatus.Internal
    private PluginService() {
        throw new UnsupportedOperationException("This class cannot be instantiated");
    }

    @ApiStatus.Internal
    public static void setService(ClusterPlugin plugin) {
        if (PluginService.plugin != null) {
            return;
        }

        PluginService.plugin = plugin;
    }

    @ApiStatus.Internal
    public static void stopService() {
        if (PluginService.plugin == null) {
            return;
        }

        PluginService.plugin = null;
    }
}