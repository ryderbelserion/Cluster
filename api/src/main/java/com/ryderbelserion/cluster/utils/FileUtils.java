package com.ryderbelserion.cluster.utils;

import com.ryderbelserion.cluster.Cluster;
import com.ryderbelserion.cluster.ClusterProvider;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;

public class FileUtils {

    private static final Cluster provider = ClusterProvider.get();

    public static void copyFiles(Path directory, String folder, List<String> names) {
        names.forEach(name -> copyFile(directory, folder, name));
    }

    /**
     * @return A list of any file.
     */
    public static List<String> getFiles(File dataFolder, String folder) {
        List<String> files = new ArrayList<>();

        File crateDirectory = new File(dataFolder, "/" + folder);

        String[] file = crateDirectory.list();

        if (file != null) {
            File[] filesList = crateDirectory.listFiles();

            if (filesList != null) {
                for (File directory : filesList) {
                    if (directory.isDirectory()) {
                        String[] folderList = directory.list();

                        if (folderList != null) {
                            for (String name : folderList) {
                                if (!name.endsWith(".yml")) continue;

                                files.add(name.replaceAll(".yml", ""));
                            }
                        }
                    }
                }
            }

            for (String name : file) {
                if (!name.endsWith(".yml")) continue;

                files.add(name.replaceAll(".yml", ""));
            }
        }

        return Collections.unmodifiableList(files);
    }

    public static void copyFile(Path directory, String name) {
        File file = directory.resolve(name).toFile();

        if (file.exists()) return;

        File dir = directory.toFile();

        if (!dir.exists()) {
            if (dir.mkdirs()) {
                if (provider.isLogging()) provider.getLogger().warning("Created " + dir.getName() + " because we couldn't find it.");
            }
        }

        ClassLoader loader = provider.getClass().getClassLoader();

        getResource(name, file, loader);
    }

    private static void getResource(String name, File file, ClassLoader loader) {
        URL resource = loader.getResource(name);

        if (resource == null) {
            if (provider.isLogging()) provider.getLogger().severe("Failed to find file: " + name);

            return;
        }

        try {
            grab(resource.openStream(), file);
        } catch (Exception exception) {
            provider.getLogger().log(Level.SEVERE, "Failed to copy file: " + name, exception);
        }
    }

    public static void copyFile(Path directory, String folder, String name) {
        File file = directory.resolve(name).toFile();

        if (file.exists()) return;

        File dir = directory.toFile();

        if (!dir.exists()) {
            if (dir.mkdirs()) {
                if (provider.isLogging()) provider.getLogger().warning("Created " + dir.getName() + " because we couldn't find it.");
            }
        }

        ClassLoader loader = provider.getClass().getClassLoader();

        String url = folder + "/" + name;

        getResource(url, file, loader);
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