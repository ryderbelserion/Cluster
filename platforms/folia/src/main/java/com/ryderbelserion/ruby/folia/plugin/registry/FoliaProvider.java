package com.ryderbelserion.ruby.folia.plugin.registry;

import com.ryderbelserion.ruby.folia.FoliaImpl;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

public class FoliaProvider {

    private static FoliaImpl paper = null;

    public static @NotNull FoliaImpl get() {
        FoliaImpl instance = FoliaProvider.paper;

        if (instance == null) throw new RuntimeException("Failed to utilize paper provider. Did it get enabled?");

        return paper;
    }

    @ApiStatus.Internal
    public static void start(FoliaImpl paper) {
        FoliaProvider.paper = paper;
    }

    @ApiStatus.Internal
    public static void stop() {
        FoliaProvider.paper = null;
    }
}