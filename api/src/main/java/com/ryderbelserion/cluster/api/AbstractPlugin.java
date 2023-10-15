package com.ryderbelserion.cluster.api;

import com.ryderbelserion.cluster.api.config.FileManager;
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

public abstract class AbstractPlugin implements PluginBase {

    public abstract FileManager getFileManager();

    @Override
    public void copyResource(Path directory, String folder, String name) {
        File file = directory.resolve(name).toFile();

        if (file.exists()) return;

        File dir = directory.toFile();

        if (!dir.exists()) {
            dir.mkdirs();
        }

        String path = folder + "/" + name;

        InputStream resource = getResource(path);

        try (FileOutputStream stream = new FileOutputStream(file)) {
            byte[] buffer = new byte[1024];
            int amount;

            while ((amount = resource.read(buffer)) > 0) {
                stream.write(buffer, 0, amount);
            }

            resource.close();
        } catch (IOException exception) {
            getLogger().log(Level.SEVERE, "Could not save " + file.getName() + " to " + dir.getPath(), exception);
        }
    }

    @Override
    public InputStream getResource(String path) {
        if (path == null) throw new IllegalArgumentException("Path cannot be null");

        try {
            URL url = getClass().getClassLoader().getResource(path);

            if (url == null) return null;

            URLConnection connection = url.openConnection();
            connection.setUseCaches(false);
            return connection.getInputStream();
        } catch (IOException exception) {
            return null;
        }
    }

    @Override
    public String convertList(List<String> list) {
        StringBuilder message = new StringBuilder();

        for (String line : list) {
            message.append(line).append("\n");
        }

        return message.toString();
    }

    @Override
    @NotNull
    public Component parse(String message) {
        return MiniMessage.miniMessage().deserialize(message).decorationIfAbsent(TextDecoration.ITALIC, TextDecoration.State.FALSE);
    }

    public void enable() {
        // Start the root service.
        PluginService.setService(this);
    }

    public void disable() {
        // Stop the root service.
        PluginService.stopService();
    }
}