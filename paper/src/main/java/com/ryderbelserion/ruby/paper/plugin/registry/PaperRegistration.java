package com.ryderbelserion.ruby.paper.plugin.registry;

import com.ryderbelserion.ruby.paper.PaperPlugin;
import org.jetbrains.annotations.ApiStatus;

import java.lang.reflect.Method;

public class PaperRegistration {

    private static final Method start;
    private static final Method stop;

    static {
        try {
            start = PaperProvider.class.getDeclaredMethod("start", PaperPlugin.class);
            start.setAccessible(true);

            stop = PaperProvider.class.getDeclaredMethod("stop");
            stop.setAccessible(true);
        } catch (NoSuchMethodException e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    @ApiStatus.Internal
    public static void start(PaperPlugin paper) {
        try {
            start.invoke(null, paper);
        } catch (Exception exception) {
            System.out.println("[ERROR] Failed to enable paper plugin using ruby api");
            System.out.println("[ERROR] Reason: " + exception.getMessage());
        }
    }

    @ApiStatus.Internal
    public static void stop() {
        try {
            stop.invoke(null);
        } catch (Exception exception) {
            System.out.println("[ERROR] Failed to disable paper plugin using ruby api");
            System.out.println("[ERROR] Reason: " + exception.getMessage());
        }
    }
}