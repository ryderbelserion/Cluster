package com.ryderbelserion.ruby.minecraft.plugin.registry;

import com.ryderbelserion.ruby.minecraft.RubyImpl;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

public class RubyProvider {

    private static RubyImpl ruby = null;

    public static @NotNull RubyImpl get() {
        RubyImpl instance = RubyProvider.ruby;

        if (instance == null) throw new RuntimeException("Failed to utilize ruby provider. Did it get enabled?");

        return ruby;
    }

    @ApiStatus.Internal
    public static void start(RubyImpl ruby) {
        RubyProvider.ruby = ruby;
    }

    @ApiStatus.Internal
    public static void stop() {
        RubyProvider.ruby = null;
    }
}