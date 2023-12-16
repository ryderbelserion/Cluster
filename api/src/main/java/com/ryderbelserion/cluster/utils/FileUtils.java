package com.ryderbelserion.cluster.utils;

import com.ryderbelserion.cluster.api.PluginService;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Path;
import java.util.logging.Level;

public class FileUtils {

    public static void copyResource(Path directory, String folder, String name) {
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
            PluginService.get().getLogger().log(Level.SEVERE, "Could not save " + file.getName() + " to " + dir.getPath(), exception);
        }
    }

    public static InputStream getResource(String path) {
        if (path == null) throw new IllegalArgumentException("Path cannot be null");

        try {
            URL url = FileUtils.class.getClassLoader().getResource(path);

            if (url == null) return null;

            URLConnection connection = url.openConnection();
            connection.setUseCaches(false);
            return connection.getInputStream();
        } catch (IOException exception) {
            return null;
        }
    }
}