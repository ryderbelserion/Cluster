package com.ryderbelserion.ruby.paper.plugin.registry;

import com.ryderbelserion.ruby.paper.PaperPlugin;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

public class PaperProvider {

    private static PaperPlugin paper = null;

    public static @NotNull PaperPlugin get() {
        PaperPlugin instance = PaperProvider.paper;

        if (instance == null) throw new RuntimeException("Failed to utilize paper plugin using ruby api. Did it get enabled?");

        return paper;
    }

    @ApiStatus.Internal
    public static void start(PaperPlugin paper) {
        PaperProvider.paper = paper;
    }

    @ApiStatus.Internal
    public static void stop() {
        PaperProvider.paper = null;
    }
}