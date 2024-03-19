package com.ryderbelserion.cluster.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FileUtils {

    /**
     * @return A list of any file.
     */
    public static List<String> getFiles(File dataFolder, String folder) {
        List<String> files = new ArrayList<>();

        File directory = new File(dataFolder, "/" + folder);

        String[] file = directory.list();

        if (file != null) {
            File[] filesList = directory.listFiles();

            if (filesList != null) {
                for (File key : filesList) {
                    if (key.isDirectory()) {
                        String[] folderList = key.list();

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
}