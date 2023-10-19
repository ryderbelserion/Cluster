package com.ryderbelserion.cluster.paper;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

public class PaperService {

    private static PaperPlugin paperPlugin = null;

    public static @NotNull PaperPlugin get() {
        PaperPlugin instance = PaperService.paperPlugin;

        if (instance == null)  {
            throw new RuntimeException("The API has not been properly initialized! Likely did not use the setService method.");
        }

        return paperPlugin;
    }

    @ApiStatus.Internal
    private PaperService()  {
        throw new UnsupportedOperationException("This class cannot be instantiated");
    }

    @ApiStatus.Internal
    public static void setService(PaperPlugin paperPlugin) {
        if (PaperService.paperPlugin != null) {
            return;
        }

        PaperService.paperPlugin = paperPlugin;
    }

    @ApiStatus.Internal
    public static void stopService() {
        if (PaperService.paperPlugin == null) {
            return;
        }

        PaperService.paperPlugin = null;
    }
}