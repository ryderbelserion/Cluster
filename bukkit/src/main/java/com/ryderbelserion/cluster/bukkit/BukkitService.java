package com.ryderbelserion.cluster.bukkit;

import com.ryderbelserion.cluster.api.adventure.FancyLogger;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

public class BukkitService {

    private static BukkitPlugin bukkitPlugin = null;

    public static @NotNull BukkitPlugin getService() {
        BukkitPlugin instance = BukkitService.bukkitPlugin;

        if (instance == null)  {
            throw new RuntimeException("Cluster bukkit service not set. Please call the method setService before you try to use it!");
        }

        return bukkitPlugin;
    }

    @ApiStatus.Internal
    private BukkitService()  {
        throw new UnsupportedOperationException("This class cannot be instantiated");
    }

    @ApiStatus.Internal
    public static void setService(BukkitPlugin bukkit) {
        if (BukkitService.bukkitPlugin != null) {
            FancyLogger.error("Cluster's bukkit service is not null, You cannot override it.");

            return;
        }

        BukkitService.bukkitPlugin = bukkit;
    }

    @ApiStatus.Internal
    public static void stopService() {
        if (BukkitService.bukkitPlugin == null) {
            return;
        }

        BukkitService.bukkitPlugin = null;
    }
}