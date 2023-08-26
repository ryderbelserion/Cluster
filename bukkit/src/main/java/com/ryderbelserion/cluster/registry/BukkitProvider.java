package com.ryderbelserion.cluster.registry;

import com.ryderbelserion.cluster.BukkitPlugin;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

public class BukkitProvider {

    private static BukkitPlugin bukkit = null;

    public static @NotNull BukkitPlugin get() {
        BukkitPlugin instance = BukkitProvider.bukkit;

        if (instance == null) {
            return null;
        }

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