package com.ryderbelserion.cluster.api;

import net.kyori.adventure.audience.Audience;

import java.io.File;
import java.util.logging.Logger;

public abstract class ClusterPlugin {

    public abstract boolean isLogging();

    public abstract Logger getLogger();

    public abstract File getDataFolder();

    public abstract Audience getConsole();

    public abstract void enable();

    public abstract void disable();

    public void start() {
        // Start the root service.
        PluginService.setService(this);
    }

    public void stop() {
        // Stop the root service.
        PluginService.stopService();
    }
}