package com.ryderbelserion.ruby.minecraft.plugin.registry;

import com.ryderbelserion.ruby.minecraft.RubyImpl;
import org.jetbrains.annotations.ApiStatus;
import java.lang.reflect.Method;

public class RubyRegistration {

    private static final Method start;
    private static final Method stop;

    static {
        try {
            start = RubyProvider.class.getDeclaredMethod("start", RubyImpl.class);
            start.setAccessible(true);

            stop = RubyProvider.class.getDeclaredMethod("stop");
            stop.setAccessible(true);
        } catch (NoSuchMethodException e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    @ApiStatus.Internal
    public static void start(RubyImpl ruby) {
        try {
            start.invoke(null, ruby);
        } catch (Exception exception) {
            System.out.println("[ERROR] Failed to enable ruby provider");
            System.out.println("[ERROR] Reason: " + exception.getMessage());
        }
    }

    @ApiStatus.Internal
    public static void stop() {
        try {
            stop.invoke(null);
        } catch (Exception exception) {
            System.out.println("Failed to disable ruby provider");
            System.out.println("Reason: " + exception.getMessage());
        }
    }
}