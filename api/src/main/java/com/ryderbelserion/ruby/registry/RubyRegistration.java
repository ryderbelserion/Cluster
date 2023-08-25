package com.ryderbelserion.ruby.registry;

import com.ryderbelserion.ruby.RubyPlugin;
import org.jetbrains.annotations.ApiStatus;
import java.lang.reflect.Method;

public class RubyRegistration {

    private static final Method start;
    private static final Method stop;

    static {
        try {
            start = RubyProvider.class.getDeclaredMethod("start", RubyPlugin.class);
            start.setAccessible(true);

            stop = RubyProvider.class.getDeclaredMethod("stop");
            stop.setAccessible(true);
        } catch (NoSuchMethodException e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    @ApiStatus.Internal
    public static void start(RubyPlugin rubyPlugin) {
        try {
            start.invoke(null, rubyPlugin);
        } catch (Exception exception) {
            System.out.println("[ERROR] Failed to enable ruby plugin");
            System.out.println("[ERROR] Reason: " + exception.getMessage());
        }
    }

    @ApiStatus.Internal
    public static void stop() {
        try {
            stop.invoke(null);
        } catch (Exception exception) {
            System.out.println("[ERROR] Failed to disable ruby plugin");
            System.out.println("[ERROR] Reason: " + exception.getMessage());
        }
    }
}