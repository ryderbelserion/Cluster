package com.ryderbelserion.cluster.api.interfaces;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import java.io.File;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.List;
import java.util.logging.Logger;

public interface PluginBase {

    Logger getLogger();

    File getDataFolder();

    Audience getConsole();

    void enable();

    void disable();

}