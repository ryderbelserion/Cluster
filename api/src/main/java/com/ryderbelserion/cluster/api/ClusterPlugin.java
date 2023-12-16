package com.ryderbelserion.cluster.api;

import com.ryderbelserion.cluster.api.interfaces.PluginBase;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.jetbrains.annotations.NotNull;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Path;
import java.util.List;
import java.util.logging.Level;

public abstract class ClusterPlugin implements PluginBase {

    public abstract boolean isLogging();

    public void enable() {
        // Start the root service.
        PluginService.setService(this);
    }

    public void disable() {
        // Stop the root service.
        PluginService.stopService();
    }
}