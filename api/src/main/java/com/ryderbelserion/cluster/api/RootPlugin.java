package com.ryderbelserion.cluster.api;

import com.ryderbelserion.cluster.api.config.FileManager;
import com.ryderbelserion.cluster.api.utils.FileUtils;
import net.kyori.adventure.audience.Audience;
import java.nio.file.Path;

public abstract class RootPlugin {

    public abstract FileManager getFileManager();
    public abstract FileUtils getFileUtils();
    public abstract Path getFolder();

    private static Audience console;

    public static void setConsole(Audience console) {
        if (RootPlugin.console != null) return;

        // Bind console to whatever the server impl's console sender is.
        RootPlugin.console = console;
    }

    public void enable() {
        // Start the root service.
        RootService.setService(this);
    }

    public void disable() {
        // Stop the root service.
        RootService.stopService();
    }

    public static Audience getConsole() {
        return console;
    }
}