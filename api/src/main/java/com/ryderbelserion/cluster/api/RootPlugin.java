package com.ryderbelserion.cluster.api;

import com.ryderbelserion.cluster.api.adventure.FancyLogger;
import com.ryderbelserion.cluster.api.config.FileManager;
import com.ryderbelserion.cluster.api.registry.RootRegistry;
import net.kyori.adventure.audience.Audience;
import java.nio.file.Path;

public abstract class RootPlugin {

    public abstract FileManager getFileManager();
    public abstract Path getFolder();

    private static Audience console;

    public void enable(Audience console, String name) {
        // Bind console to whatever the server impl's console sender is.
        RootPlugin.console = console;

        // Set fancy logger name.
        new FancyLogger(name);

        // Start registry.
        RootRegistry.start(this);
    }

    public void disable() {
        // Stop registry.
        RootRegistry.stop();
    }

    public static Audience getConsole() {
        return console;
    }
}