package com.ryderbelserion.ruby.folia.plugin.registry;

import com.ryderbelserion.ruby.folia.FoliaImpl;
import org.jetbrains.annotations.ApiStatus;

import java.lang.reflect.Method;

public class FoliaRegistration {

    private static final Method start;
    private static final Method stop;

    static {
        try {
            start = FoliaProvider.class.getDeclaredMethod("start", FoliaImpl.class);
            start.setAccessible(true);

            stop = FoliaProvider.class.getDeclaredMethod("stop");
            stop.setAccessible(true);
        } catch (NoSuchMethodException e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    @ApiStatus.Internal
    public static void start(FoliaImpl paper) {
        try {
            start.invoke(null, paper);
        } catch (Exception exception) {
            System.out.println("[ERROR] Failed to enable folia provider");
            System.out.println("[ERROR] Reason: " + exception.getMessage());
        }
    }

    @ApiStatus.Internal
    public static void stop() {
        try {
            stop.invoke(null);
        } catch (Exception exception) {
            System.out.println("[ERROR] Failed to disable folia provider");
            System.out.println("[ERROR] Reason: " + exception.getMessage());
        }
    }
}