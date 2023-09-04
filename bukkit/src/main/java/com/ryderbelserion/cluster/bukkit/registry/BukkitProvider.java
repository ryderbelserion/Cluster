package com.ryderbelserion.cluster.bukkit.registry;

import com.ryderbelserion.cluster.api.adventure.FancyLogger;
import com.ryderbelserion.cluster.bukkit.BukkitPlugin;
import org.jetbrains.annotations.NotNull;
import static org.jetbrains.annotations.ApiStatus.Internal;

public class BukkitProvider {

    @Internal
    private BukkitProvider()  {
        throw new UnsupportedOperationException("This class cannot be instantiated");
    }

    private static BukkitPlugin bukkit = null;

    public static @NotNull BukkitPlugin get() {
        BukkitPlugin instance = BukkitProvider.bukkit;

        if (instance == null) throw new NotYetAvailable();

        return bukkit;
    }

    @Internal
    public static void start(BukkitPlugin bukkit) {
        if (BukkitProvider.bukkit != null) {
            FancyLogger.error("Cluster already has a variable assigned to it! You cannot override it.");
            return;
        }

        BukkitProvider.bukkit = bukkit;
    }

    @Internal
    public static void stop() {
        if (BukkitProvider.bukkit == null) {
            FancyLogger.error("Cluster cannot be set as null because it is already null.");
            return;
        }

        BukkitProvider.bukkit = null;
    }

    private static final class NotYetAvailable extends IllegalStateException {

        private static final String message = """
                Cluster API isn't available!
                A few reasons of why this could be happening:
                 1. The plugin failed to enable or is not in the plugins folder.
                 2. The plugin trying to use Cluster doesn't depend on Cluster.
                 3. The plugin trying to use Cluster is using the API before Cluster is enabled.
                 """;

        NotYetAvailable() {
            super(message);
        }
    }
}