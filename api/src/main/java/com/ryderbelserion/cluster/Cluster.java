package com.ryderbelserion.cluster;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Path;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class Cluster {

    public Cluster() {
        // Register provider.
        ClusterProvider.register(this);
    }

    public void disable() {
        // Unregister provider.
        ClusterProvider.unregister();
    }

    public void copyFiles(Path directory, String folder, List<String> names) {
        names.forEach(name -> copyFile(directory, folder, name));
    }

    public void copyFile(Path directory, String folder, String name) {
        copyFile(directory, folder + "/" + name);
    }

    public void copyFile(Path directory, String name) {
        File file = directory.resolve(name).toFile();

        if (file.exists()) return;

        File dir = directory.toFile();

        if (!dir.exists()) {
            if (dir.mkdirs()) {
                if (isLogging()) getLogger().warning("Created " + dir.getName() + " because we couldn't find it.");
            }
        }

        ClassLoader loader = getClass().getClassLoader();

        getResource(name, file, loader);
    }

    private void getResource(String name, File file, ClassLoader loader) {
        URL resource = loader.getResource(name);

        if (resource == null) {
            if (isLogging()) getLogger().severe("Failed to find file: " + name);

            return;
        }

        try {
            grab(resource.openStream(), file);
        } catch (Exception exception) {
            getLogger().log(Level.SEVERE, "Failed to copy file: " + name, exception);
        }
    }

    private void grab(InputStream input, File output) throws Exception {
        try (InputStream inputStream = input; FileOutputStream outputStream = new FileOutputStream(output)) {
            byte[] buf = new byte[1024];
            int i;

            while ((i = inputStream.read(buf)) != -1) {
                outputStream.write(buf, 0, i);
            }
        }
    }

    public abstract boolean isLogging();

    public abstract void setLogging(boolean isLogging);

    public abstract boolean isPapiEnabled();

    public abstract boolean isOraxenEnabled();

    public abstract boolean isItemsAdderEnabled();

    public abstract boolean isHeadDatabaseEnabled();

    public abstract void saveResource(String file, boolean overwrite);

    public abstract Logger getLogger();

    public abstract File getFolder();
}