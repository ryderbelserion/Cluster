package com.ryderbelserion.cluster.api.files;

import com.ryderbelserion.cluster.Cluster;
import com.ryderbelserion.cluster.ClusterProvider;
import org.jetbrains.annotations.NotNull;
import org.simpleyaml.configuration.file.FileConfiguration;
import org.simpleyaml.configuration.file.YamlConfiguration;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileManager {

    private final @NotNull Cluster provider = ClusterProvider.get();

    private final @NotNull File dataFolder = this.provider.getFolder();

    private final @NotNull Logger logger = this.provider.getLogger();

    private final boolean isLogging = this.provider.isLogging();

    private final Map<String, FileConfiguration> configurations = new HashMap<>();
    private final Map<String, String> dynamicFiles = new HashMap<>();
    private final Set<CustomFile> customFiles = new HashSet<>();
    private final Set<String> staticFiles = new HashSet<>();
    private final Set<String> folders = new HashSet<>();

    public void create() {
        if (!this.dataFolder.exists()) this.dataFolder.mkdirs();

        this.configurations.clear();
        this.customFiles.clear();

        for (String file : this.staticFiles) {
            File newFile = new File(this.dataFolder, file);

            if (this.isLogging) this.logger.info("Loading static file: " + newFile.getName());

            if (!newFile.exists()) {
                try {
                    this.provider.saveResource(file, false);

                    loadFile(file, newFile);
                } catch (Exception exception) {
                    this.logger.log(Level.SEVERE, "Failed to load: " + newFile.getName(), exception);

                    continue;
                }
            } else {
                loadFile(file, newFile);
            }

            if (this.isLogging) this.logger.fine("Successfully loaded: " + newFile.getName());
        }

        // If folders is empty, return.
        if (this.folders.isEmpty()) {
            this.logger.warning("I seem to not have any folders to work with.");

            return;
        }

        // Loop through folders hash-set and create folders.
        for (String folder : this.folders) {
            File newFolder = new File(dataFolder, "/" + folder);

            // Check if the directory exists.
            if (newFolder.exists()) {
                File[] list = newFolder.listFiles((dir, name) -> name.endsWith(".yml"));

                if (list != null) {
                    for (File file : list) {
                        CustomFile customFile = new CustomFile(file.getName(), folder);

                        if (customFile.exists()) {
                            if (this.isLogging) this.logger.info("Loading custom file: " + file.getName());

                            addDynamicFile(customFile);
                        }
                    }
                }
            } else if (newFolder.mkdir()) {
                if (this.isLogging) this.logger.info("Created " + newFolder.getName() + " because it did not exist.");

                for (String fileName : this.dynamicFiles.keySet()) {
                    if (this.dynamicFiles.get(fileName).equalsIgnoreCase(folder)) {
                        folder = this.dynamicFiles.get(fileName);

                        try {
                            File newFile = new File(dataFolder, folder + "/" + fileName);

                            this.provider.saveResource(folder + "/" + fileName, false);

                            if (newFile.getName().toLowerCase().endsWith(".yml")) {
                                CustomFile customFile = new CustomFile(newFile.getName(), folder);

                                addDynamicFile(customFile);
                            }

                            if (this.isLogging) this.logger.info("Created default file: " + newFile.getPath() + ".");
                        } catch (Exception exception) {
                            this.logger.log(Level.SEVERE, "Failed to create default file: " + folder + "/" + fileName + "!", exception);
                        }
                    }
                }
            }
        }

        if (this.isLogging) this.logger.info("Finished loading custom files.");
    }

    private void loadFile(String file, File newFile) {
        YamlConfiguration configuration = CompletableFuture.supplyAsync(() -> {
            try {
                return YamlConfiguration.loadConfiguration(newFile);
            } catch (IOException exception) {
                this.logger.log(Level.WARNING, "Failed to load " + newFile.getName() + ".", exception);
            }

            return null;
        }).join();

        this.configurations.put(file, configuration);
    }

    public FileManager addFolder(String folder) {
        if (this.folders.contains(folder)) {
            this.logger.warning("The folder named: " + folder + " already exists.");

            return this;
        }

        this.folders.add(folder);

        return this;
    }

    public FileManager removeFolder(String folder) {
        if (!this.folders.contains(folder)) {
            this.logger.warning("The folder named: " + folder + " is not known to me.");

            return this;
        }

        this.folders.remove(folder);

        return this;
    }

    public Set<String> getFolders() {
        return Collections.unmodifiableSet(this.folders);
    }

    // Adds default files.
    public FileManager addDynamicFile(String folder, String file) {
        this.dynamicFiles.put(file, folder);

        return this;
    }

    public FileManager saveDynamicFile(String file) {
        CustomFile customFile = getDynamicFile(file);

        if (customFile == null) {
            if (this.isLogging) this.logger.warning("The file " + file + ".yml could not be found!");

            return this;
        }

        try {
            File newFile = new File(this.dataFolder, customFile.getFolder() + "/" + customFile.getFileName());

            customFile.getConfiguration().save(newFile);
        } catch (IOException exception) {
            this.logger.log(Level.SEVERE, "Could not save " + customFile.getFileName() + "!", exception);
        }

        return this;
    }

    public FileManager reloadDynamicFiles() {
        this.customFiles.forEach(CustomFile::reload);

        return this;
    }

    public FileManager reloadDynamicFile(String file) {
        CustomFile customFile = getDynamicFile(file);

        if (customFile == null) {
            if (this.isLogging) this.logger.warning("The file " + file + ".yml could not be found!");

            return this;
        }

        try {
            File newFile = new File(this.dataFolder, customFile.getFolder() + "/" + customFile.getFileName());

            YamlConfiguration configuration = CompletableFuture.supplyAsync(() -> {
                try {
                    return YamlConfiguration.loadConfiguration(newFile);
                } catch (IOException exception) {
                    this.logger.log(Level.WARNING, "Failed to load " + newFile.getName() + ".", exception);
                }

                return null;
            }).join();

            customFile.setConfiguration(configuration);

            if (this.isLogging) this.logger.info("Successfully reloaded " + customFile.getFileName() + ".");
        } catch (Exception exception) {
            this.logger.log(Level.SEVERE, "Could not save " + customFile.getFileName() + "!", exception);
        }

        return this;
    }

    public CustomFile getDynamicFile(String file) {
        for (CustomFile customFile : this.customFiles) {
            if (customFile.getFileName().equalsIgnoreCase(file)) return customFile;
        }

        return null;
    }

    // Adds already loaded files.
    public FileManager addDynamicFile(CustomFile customFile) {
        this.customFiles.add(customFile);

        return this;
    }

    public Map<String, String> getDynamicFiles() {
        return Collections.unmodifiableMap(this.dynamicFiles);
    }

    public FileManager addStaticFile(String file) {
        this.staticFiles.add(file);

        return this;
    }

    public FileConfiguration getStaticFile(String file) {
        return this.configurations.get(file);
    }

    public FileManager saveStaticFile(String file) {
        try {
            File newFile = new File(this.dataFolder, "/" + file);

            this.configurations.get(file).save(newFile);
        } catch (IOException exception) {
            this.logger.log(Level.SEVERE, "Failed to save " + file + "!", exception);
        }

        return this;
    }

    public FileManager saveStaticFile(String folder, String file) {
        try {
            File newFile = new File(this.dataFolder, folder + "/" + file);

            this.configurations.get(file).save(newFile);
        } catch (IOException exception) {
            this.logger.log(Level.SEVERE, "Failed to save " + folder + "/" + file + "!", exception);
        }

        return this;
    }

    public FileManager reloadStaticFile(String folder, String file) {
        File newFile = new File(this.dataFolder, folder + "/" + file);

        loadFile(file, newFile);

        return this;
    }

    public FileManager reloadStaticFile(String file) {
        File newFile = new File(this.dataFolder, "/" + file);

        loadFile(file, newFile);

        return this;
    }

    public FileManager clearStaticFiles() {
        this.staticFiles.clear();

        return this;
    }
}