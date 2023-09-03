package com.ryderbelserion.cluster.bukkit.registry;

import com.ryderbelserion.cluster.bukkit.BukkitPlugin;
import com.ryderbelserion.cluster.api.adventure.FancyLogger;
import org.jetbrains.annotations.ApiStatus;
import java.lang.reflect.Method;

public class BukkitRegistry {

    private static final Method start;
    private static final Method stop;

    static {
        try {
            start = BukkitProvider.class.getDeclaredMethod("start", BukkitPlugin.class);
            start.setAccessible(true);

            stop = BukkitProvider.class.getDeclaredMethod("stop");
            stop.setAccessible(true);
        } catch (NoSuchMethodException e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    @ApiStatus.Internal
    public static void start(BukkitPlugin bukkit) {
        try {
            start.invoke(null, bukkit);
        } catch (Exception exception) {
            FancyLogger.error("Failed to enable cluster bukkit api.");
            FancyLogger.debug("Reason: " + exception.getMessage());
        }
    }

    @ApiStatus.Internal
    public static void stop() {
        try {
            stop.invoke(null);
        } catch (Exception exception) {
            FancyLogger.error("Failed to disable cluster bukkit api.");
            FancyLogger.debug("Reason: " + exception.getMessage());
        }
    }
}