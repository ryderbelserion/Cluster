package com.ryderbelserion.cluster.paper.files;

import com.ryderbelserion.cluster.paper.AbstractPaperPlugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;

public class FileManager {

    private final JavaPlugin javaPlugin;
    private final AbstractPaperPlugin plugin;

    public FileManager(AbstractPaperPlugin plugin, JavaPlugin javaPlugin) {
        this.plugin = plugin;

        this.javaPlugin = javaPlugin;
    }

    private final HashSet<String> folders = new HashSet<>();
    private final HashSet<String> staticFiles = new HashSet<>();
    private final HashMap<String, String> dynamicFiles = new HashMap<>();

    public void create() {
        if (!this.plugin.getDataFolder().exists()) this.plugin.getDataFolder().mkdirs();

        // If folders is empty, return.
        if (this.folders.isEmpty()) {
            this.plugin.getLogger().warning("I seem to not have any folders to work with.");
            return;
        }

        for (String file : this.staticFiles) {
            File newFile = new File(this.plugin.getDataFolder(), file);

            if (this.plugin.isLogging()) this.plugin.getLogger().info("Loading " + newFile.getName());

            if (!newFile.exists()) {
                try {
                    this.javaPlugin.saveResource(file, false);
                } catch (Exception exception) {
                    this.plugin.getLogger().log(Level.SEVERE, "Failed to load: " + newFile.getName(), exception);

                    continue;
                }
            }

            if (this.plugin.isLogging()) this.plugin.getLogger().fine("Successfully loaded: " + newFile.getName());
        }

        // Loop through folders hash-set and create folders.
        for (String folder : this.folders) {
            File newFolder = new File(this.plugin.getDataFolder(), "/" + folder);

            // Check if the directory exists.
            if (newFolder.exists()) {
                File[] list = newFolder.listFiles((dir, name) -> name.endsWith(".yml"));

                if (list != null) {
                    for (File file : list) {
                        if (file.exists()) {
                            if (this.plugin.isLogging()) this.plugin.getLogger().info("Loading file: " + file.getName());

                            addDynamicFile(folder, file.getName());
                        }
                    }
                }
            } else if (newFolder.mkdir()) {
                if (this.plugin.isLogging()) this.plugin.getLogger().info("Created " + newFolder.getName() + " because it did not exist.");

                for (String fileName : this.dynamicFiles.keySet()) {
                    if (this.dynamicFiles.get(fileName).equalsIgnoreCase(folder)) {
                        folder = this.dynamicFiles.get(fileName);

                        try {
                            File newFile = new File(this.plugin.getDataFolder(), folder + "/" + fileName);

                            this.javaPlugin.saveResource(folder + "/" + fileName, false);

                            if (newFile.getName().toLowerCase().endsWith(".yml")) addDynamicFile(folder, newFile.getName());

                            if (this.plugin.isLogging()) this.plugin.getLogger().info("Created default file: " + newFile.getPath() + ".");
                        } catch (Exception exception) {
                            this.plugin.getLogger().log(Level.SEVERE, "Failed to create default file: " + folder + "/" + fileName + "!", exception);
                        }
                    }
                }
            }
        }
    }

    public FileManager addFolder(String folder) {
        if (this.folders.contains(folder)) {
            this.plugin.getLogger().warning("The folder named: " + folder + " already exists.");
            return this;
        }

        this.folders.add(folder);

        return this;
    }

    public void removeFolder(String folder) {
        if (!this.folders.contains(folder)) {
            this.plugin.getLogger().warning("The folder named: " + folder + " is not known to me.");
            return;
        }

        this.folders.remove(folder);
    }

    public Set<String> getFolders() {
        return Collections.unmodifiableSet(this.folders);
    }

    public FileManager addDynamicFile(String folder, String file) {
        this.dynamicFiles.put(file, folder);

        return this;
    }

    public Map<String, String> getDynamicFiles() {
        return Collections.unmodifiableMap(this.dynamicFiles);
    }

    public FileManager addStaticFile(String file) {
        this.staticFiles.add(file);

        return this;
    }

    public void clearStaticFiles() {
        this.staticFiles.clear();
    }
}