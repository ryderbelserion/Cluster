package com.ryderbelserion.cluster;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

public class ClusterService {

    private static ICluster plugin = null;

    /**
     * Fetches the instance of the interface for api use.
     *
     * @return the instance of ICluster
     */
    public static @NotNull ICluster get() {
        ICluster instance = ClusterService.plugin;

        if (instance == null) {
            throw new RuntimeException("Cluster service method not set. Please call the method setService before you try to use it!");
        }

        return plugin;
    }

    /**
     * Prevents creating an instance of this class.
     */
    @ApiStatus.Internal
    private ClusterService() {
        throw new UnsupportedOperationException("This class cannot be instantiated");
    }

    /**
     * Creates the static instance of ICrazyCrates
     *
     * @param plugin instance to use
     */
    @ApiStatus.Internal
    public static void setService(ICluster plugin) {
        if (ClusterService.plugin != null) return;

        ClusterService.plugin = plugin;
    }

    /**
     * Stops the cluster service
     */
    @ApiStatus.Internal
    public static void stopService() {
        if (ClusterService.plugin == null) return;

        ClusterService.plugin = null;
    }
}