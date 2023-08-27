package com.ryderbelserion.cluster.bukkit.api;

import com.ryderbelserion.cluster.bukkit.api.adventure.FancyLogger;
import com.ryderbelserion.cluster.bukkit.api.registry.ClusterRegistry;
import net.kyori.adventure.audience.Audience;
import java.nio.file.Path;

public abstract class RootPlugin {

    public abstract Path getFolder();

    private static Audience console;

    public void enable(Audience console, String name) {
        // Bind console to whatever the server impl's console sender is.
        RootPlugin.console = console;

        // Set fancy logger name.
        new FancyLogger(name);

        // Start registry.
        ClusterRegistry.start(this);
    }

    public void disable() {
        // Stop registry.
        ClusterRegistry.stop();
    }

    public static Audience getConsole() {
        return console;
    }
}