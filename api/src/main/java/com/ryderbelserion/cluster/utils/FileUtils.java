package com.ryderbelserion.cluster.utils;

import com.ryderbelserion.cluster.Cluster;
import com.ryderbelserion.cluster.ClusterProvider;
import org.jetbrains.annotations.NotNull;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

public class FileUtils {

    public static boolean isEmpty(Path directory) throws IOException {
        if (Files.isDirectory(directory)) {
            try (Stream<Path> entries = Files.list(directory)) {
                return entries.findFirst().isEmpty();
            }
        }

        return false;
    }

    public static void copyFile(Path directory, String folder, String name) {
        File dir = directory.toFile();

        @NotNull Cluster provider = ClusterProvider.get();

        @NotNull Logger logger = provider.getLogger();

        boolean isLogging = provider.isLogging();

        if (!dir.exists()) {
            if (dir.mkdirs()) {
                if (isLogging) logger.warning("Created " + dir.getName() + " because we couldn't find it.");
            }
        }

        try {
            if (isEmpty(directory)) {
                return;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        File file = directory.resolve(name).toFile();

        if (file.exists()) return;

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