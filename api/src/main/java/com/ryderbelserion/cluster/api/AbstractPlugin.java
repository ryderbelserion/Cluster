package com.ryderbelserion.cluster.api;

import com.ryderbelserion.cluster.api.config.FileManager;
import com.ryderbelserion.cluster.api.interfaces.PluginBase;
import com.ryderbelserion.cluster.api.utils.FileUtils;
import net.kyori.adventure.audience.Audience;
import java.nio.file.Path;

public abstract class AbstractPlugin implements PluginBase {

    public abstract FileManager getFileManager();
    public abstract FileUtils getFileUtils();
    public abstract Path getFolder();

    @Override
    public void copyResource(String resourcePath, String folder) {
        if (resourcePath == null || resourcePath.isBlank()) throw new IllegalArgumentException("Resource path must not be empty.");
    }

    private static Audience console;

    public static void setConsole(Audience console) {
        if (AbstractPlugin.console != null) return;

        // Bind console to whatever the server impl's console sender is.
        AbstractPlugin.console = console;
    }

    public void enable() {
        // Start the root service.
        PluginService.setService(this);
    }

    public void disable() {
        // Stop the root service.
        PluginService.stopService();
    }

    public static Audience getConsole() {
        return console;
    }
}