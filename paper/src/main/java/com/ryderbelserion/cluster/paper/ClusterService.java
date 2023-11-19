package com.ryderbelserion.cluster.paper;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

public class ClusterService {

    private static ClusterFactory clusterFactory = null;

    public static @NotNull ClusterFactory get() {
        ClusterFactory instance = ClusterService.clusterFactory;

        if (instance == null)  {
            throw new RuntimeException("The API has not been properly initialized! Likely did not use the setService method.");
        }

        return clusterFactory;
    }

    @ApiStatus.Internal
    private ClusterService()  {
        throw new UnsupportedOperationException("This class cannot be instantiated");
    }

    @ApiStatus.Internal
    public static void setService(ClusterFactory clusterFactory) {
        if (ClusterService.clusterFactory != null) {
            return;
        }

        ClusterService.clusterFactory = clusterFactory;
    }

    @ApiStatus.Internal
    public static void stopService() {
        if (ClusterService.clusterFactory == null) {
            return;
        }

        ClusterService.clusterFactory = null;
    }
}