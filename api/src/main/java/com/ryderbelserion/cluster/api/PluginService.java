package com.ryderbelserion.cluster.api;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

public class PluginService {

    private static ClusterPlugin clusterPlugin = null;

    public static @NotNull ClusterPlugin get() {
        ClusterPlugin instance = PluginService.clusterPlugin;

        if (instance == null) {
            throw new RuntimeException("The API has not been properly initialized!");
        }

        return clusterPlugin;
    }

    @ApiStatus.Internal
    private PluginService() {
        throw new UnsupportedOperationException("This class cannot be instantiated");
    }

    @ApiStatus.Internal
    public static void setService(ClusterPlugin clusterPlugin) {
        if (PluginService.clusterPlugin != null) {
            return;
        }

        PluginService.clusterPlugin = clusterPlugin;
    }

    @ApiStatus.Internal
    public static void stopService() {
        if (PluginService.clusterPlugin == null) {
            return;
        }

        PluginService.clusterPlugin = null;
    }
}