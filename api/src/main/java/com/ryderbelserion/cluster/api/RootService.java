package com.ryderbelserion.cluster.api;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

public class RootService {

    private static RootPlugin rootPlugin = null;

    public static @NotNull RootPlugin getService() {
        RootPlugin instance = RootService.rootPlugin;

        if (instance == null) {
            throw new RuntimeException("Cluster root service not set. Please call the method setService before you try to use it!");
        }

        return rootPlugin;
    }

    @ApiStatus.Internal
    private RootService() {
        throw new UnsupportedOperationException("This class cannot be instantiated");
    }

    @ApiStatus.Internal
    public static void setService(RootPlugin rootPlugin) {
        if (RootService.rootPlugin != null) {
            return;
        }

        RootService.rootPlugin = rootPlugin;
    }

    @ApiStatus.Internal
    public static void stopService() {
        if (RootService.rootPlugin == null) {
            return;
        }

        RootService.rootPlugin = null;
    }
}