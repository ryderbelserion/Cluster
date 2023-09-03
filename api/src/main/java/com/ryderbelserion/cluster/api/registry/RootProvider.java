package com.ryderbelserion.cluster.api.registry;

import com.ryderbelserion.cluster.api.RootPlugin;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

public class RootProvider {

    private static RootPlugin root = null;

    public static @NotNull RootPlugin get() {
        RootPlugin instance = RootProvider.root;

        if (instance == null) throw new RuntimeException("Failed to utilize root provider. Did it get enabled?");

        return root;
    }

    @ApiStatus.Internal
    public static void start(RootPlugin root) {
        RootProvider.root = root;
    }

    @ApiStatus.Internal
    public static void stop() {
        RootProvider.root = null;
    }
}