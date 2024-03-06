package com.ryderbelserion.cluster.utils;

import com.ryderbelserion.cluster.Cluster;
import com.ryderbelserion.cluster.ClusterProvider;
import com.ryderbelserion.cluster.platform.ClusterServer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileUtils {

    public static void copyFile(Path directory, String folder, String name) {
        File file = directory.resolve(name).toFile();

        if (file.exists()) return;

        File dir = directory.toFile();

        Cluster provider = ClusterProvider.get();

        ClusterServer server = provider.getServer();

        boolean isLogging = server.isLogging();
        Logger logger = server.getLogger();

        if (!dir.exists()) {
            if (dir.mkdirs()) {
                if (isLogging) logger.warning("Created " + dir.getName() + " because we couldn't find it.");
            }
        }

        ClassLoader loader = provider.getClass().getClassLoader();

        String url = folder + "/" + name;

        URL resource = loader.getResource(url);

        if (resource == null) {
            if (isLogging) logger.severe("Failed to find file: " + url);

            return;
        }

        try {
            grab(resource.openStream(), file);
        } catch (Exception exception) {
            logger.log(Level.SEVERE, "Failed to copy file: " + url, exception);
        }
    }

    private static void grab(InputStream input, File output) throws Exception {
        try (InputStream inputStream = input; FileOutputStream outputStream = new FileOutputStream(output)) {
            byte[] buf = new byte[1024];
            int i;

            while ((i = inputStream.read(buf)) != -1) {
                outputStream.write(buf, 0, i);
            }
        }
    }
}