package com.ryderbelserion.ruby.minecraft.registry;

import com.ryderbelserion.ruby.minecraft.RubyPlugin;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

public class RubyProvider {

    private static RubyPlugin rubyPlugin = null;

    public static @NotNull RubyPlugin get() {
        RubyPlugin instance = RubyProvider.rubyPlugin;

        if (instance == null) throw new RuntimeException("Failed to utilize ruby provider. Did it get enabled?");

        return rubyPlugin;
    }

    @ApiStatus.Internal
    public static void start(RubyPlugin rubyPlugin) {
        RubyProvider.rubyPlugin = rubyPlugin;
    }

    @ApiStatus.Internal
    public static void stop() {
        RubyProvider.rubyPlugin = null;
    }
}