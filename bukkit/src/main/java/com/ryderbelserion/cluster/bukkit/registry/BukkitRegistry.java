package com.ryderbelserion.cluster.bukkit.registry;

import com.ryderbelserion.cluster.bukkit.BukkitPlugin;
import com.ryderbelserion.cluster.api.adventure.FancyLogger;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import static org.jetbrains.annotations.ApiStatus.Internal;

public class BukkitRegistry {

    private static final Method start;
    private static final Method stop;

    static {
        try {
            start = BukkitProvider.class.getDeclaredMethod("start", BukkitPlugin.class);
            start.setAccessible(true);

            stop = BukkitProvider.class.getDeclaredMethod("stop");
            stop.setAccessible(true);
        } catch (NoSuchMethodException exception) {
            throw new ExceptionInInitializerError(exception);
        }
    }

    @Internal
    public static void start(BukkitPlugin bukkit) {
        try {
            start.invoke(null, bukkit);
        } catch (IllegalAccessException | InvocationTargetException exception) {
            FancyLogger.error("Failed to enable cluster bukkit api", exception);
        }
    }

    @Internal
    public static void stop() {
        try {
            stop.invoke(null);
        } catch (IllegalAccessException | InvocationTargetException exception) {
            FancyLogger.error("Failed to disable cluster bukkit api", exception);
        }
    }
}