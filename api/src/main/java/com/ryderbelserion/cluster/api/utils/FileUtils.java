package com.ryderbelserion.cluster.api.utils;

import com.ryderbelserion.cluster.api.adventure.FancyLogger;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Path;
import java.util.List;

public class FileUtils {

    public void copyResources(Path directory, String folder, List<String> names) {
        names.forEach(name -> copyResource(directory, folder, name));
    }

    public void copyResource(Path directory, String folder, String name) {
        File file = directory.resolve(name).toFile();

        if (file.exists()) return;

        File dir = directory.toFile();

        if (!dir.exists()) {
            if (dir.mkdirs()) {
                FancyLogger.debug("Created " + dir.getName() + " because we couldn't find it.");
            }
        }

        ClassLoader loader = getClass().getClassLoader();

        String url = folder + "/" + name;

        URL resource = loader.getResource(url);

        if (resource == null) {
            FancyLogger.error("Failed to find file: " + url);

            return;
        }

        try {
            try (InputStream inputStream = resource.openStream(); FileOutputStream outputStream = new FileOutputStream(file)) {
                byte[] buf = new byte[1024];
                int i;

                while ((i = inputStream.read(buf)) != -1) {
                    outputStream.write(buf, 0, i);
                }
            }
        } catch (Exception exception) {
            FancyLogger.error("Failed to copy file: " + url, exception);
        }
    }
}