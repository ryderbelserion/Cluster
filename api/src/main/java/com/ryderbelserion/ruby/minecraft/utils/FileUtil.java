package com.ryderbelserion.ruby.minecraft.utils;

import com.ryderbelserion.ruby.minecraft.RubyImpl;
import com.ryderbelserion.ruby.other.registry.RubyProvider;
import java.io.*;
import java.net.URL;
import java.nio.file.Path;
import java.util.List;

public class FileUtil {

    private final RubyImpl ruby = RubyProvider.get();

    public void copyFiles(Path directory, String folder, List<String> names) {
        names.forEach(name -> copyFile(directory, folder, name));
    }

    public void copyFile(Path directory, String folder, String name) {
        File file = directory.resolve(name).toFile();

        if (file.exists()) return;

        File dir = directory.toFile();

        if (!dir.exists()) dir.mkdirs();

        ClassLoader loader = getClass().getClassLoader();

        String url = folder + "/" + name;

        URL resource = loader.getResource(url);

        if (resource == null) {
            switch (ruby.platform()) {
                case PAPER, SPIGOT, FABRIC -> this.ruby.logger().error("Failed to find file: " + url);
                case OTHER -> System.out.println(this.ruby.prefix() + "Failed to find file: " + url);
            }

            return;
        }

        try {
            grab(resource.openStream(), file);
        } catch (Exception exception) {
            switch (ruby.platform()) {
                case PAPER, SPIGOT, FABRIC -> this.ruby.logger().error("Failed to copy file: " + url);
                case OTHER -> System.out.println(this.ruby.prefix() + "Failed to copy file: " + url);
            }
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
}