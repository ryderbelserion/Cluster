package com.ryderbelserion.cluster.bukkit;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

public class PaperService {

    private static AbstractPaperPlugin abstractPaperPlugin = null;

    public static @NotNull AbstractPaperPlugin get() {
        AbstractPaperPlugin instance = PaperService.abstractPaperPlugin;

        if (instance == null)  {
            throw new RuntimeException("The API has not been properly initialized! Likely did not use the setService method.");
        }

        return abstractPaperPlugin;
    }

    @ApiStatus.Internal
    private PaperService()  {
        throw new UnsupportedOperationException("This class cannot be instantiated");
    }

    @ApiStatus.Internal
    public static void setService(AbstractPaperPlugin bukkit) {
        if (PaperService.abstractPaperPlugin != null) {
            return;
        }

        PaperService.abstractPaperPlugin = bukkit;
    }

    @ApiStatus.Internal
    public static void stopService() {
        if (PaperService.abstractPaperPlugin == null) {
            return;
        }

        PaperService.abstractPaperPlugin = null;
    }
}