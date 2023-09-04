package com.ryderbelserion.cluster.api.registry;

import com.ryderbelserion.cluster.api.RootPlugin;
import com.ryderbelserion.cluster.api.adventure.FancyLogger;
import org.jetbrains.annotations.ApiStatus;
import java.lang.reflect.Method;

public class RootRegistry {

    private static final Method start;
    private static final Method stop;

    static {
        try {
            start = RootProvider.class.getDeclaredMethod("start", RootPlugin.class);
            start.setAccessible(true);

            stop = RootProvider.class.getDeclaredMethod("stop");
            stop.setAccessible(true);
        } catch (NoSuchMethodException exception) {
            throw new ExceptionInInitializerError(exception);
        }
    }

    @ApiStatus.Internal
    public static void start(RootPlugin root) {
        try {
            start.invoke(null, root);
        } catch (Exception exception) {
            FancyLogger.error("Failed to enable cluster root plugin.");
            FancyLogger.debug("Reason: " + exception.getMessage());
        }
    }

    @ApiStatus.Internal
    public static void stop() {
        try {
            stop.invoke(null);
        } catch (Exception exception) {
            FancyLogger.error("Failed to disable cluster root plugin.");
            FancyLogger.debug("Reason: " + exception.getMessage());
        }
    }
}