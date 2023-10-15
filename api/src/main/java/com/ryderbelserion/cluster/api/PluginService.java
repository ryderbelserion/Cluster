package com.ryderbelserion.cluster.api;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

public class PluginService {

    private static AbstractPlugin abstractPlugin = null;

    public static @NotNull AbstractPlugin getService() {
        AbstractPlugin instance = PluginService.abstractPlugin;

        if (instance == null) {
            throw new RuntimeException("Cluster root service not set. Please call the method setService before you try to use it!");
        }

        return abstractPlugin;
    }

    @ApiStatus.Internal
    private PluginService() {
        throw new UnsupportedOperationException("This class cannot be instantiated");
    }

    @ApiStatus.Internal
    public static void setService(AbstractPlugin abstractPlugin) {
        if (PluginService.abstractPlugin != null) {
            return;
        }

        PluginService.abstractPlugin = abstractPlugin;
    }

    @ApiStatus.Internal
    public static void stopService() {
        if (PluginService.abstractPlugin == null) {
            return;
        }

        PluginService.abstractPlugin = null;
    }
}