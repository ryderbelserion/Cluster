package com.ryderbelserion.cluster.bukkit.api.registry;

import com.ryderbelserion.cluster.bukkit.api.RootPlugin;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

public class ClusterProvider {

    private static RootPlugin root = null;

    public static @NotNull RootPlugin get() {
        RootPlugin instance = ClusterProvider.root;

        if (instance == null) throw new RuntimeException("Failed to utilize root provider. Did it get enabled?");

        return root;
    }

    @ApiStatus.Internal
    public static void start(RootPlugin root) {
        ClusterProvider.root = root;
    }

    @ApiStatus.Internal
    public static void stop() {
        ClusterProvider.root = null;
    }
}