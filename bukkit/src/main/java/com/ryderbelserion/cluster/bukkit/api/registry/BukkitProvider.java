package com.ryderbelserion.cluster.bukkit.api.registry;

import com.ryderbelserion.cluster.bukkit.BukkitPlugin;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

public class BukkitProvider {

    private static BukkitPlugin bukkit = null;

    public static @NotNull BukkitPlugin get() {
        BukkitPlugin instance = BukkitProvider.bukkit;

        if (instance == null) throw new RuntimeException("Cluster provider is null.");

        return bukkit;
    }

    @ApiStatus.Internal
    public static void start(BukkitPlugin bukkit) {
        BukkitProvider.bukkit = bukkit;
    }

    @ApiStatus.Internal
    public static void stop() {
        BukkitProvider.bukkit = null;
    }
}