package com.ryderbelserion.cluster.api.files;

import com.ryderbelserion.cluster.ClusterFactory;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;

public class CustomFile {

    private final ClusterFactory plugin;

    private final String strippedName;
    private final String fileName;
    private final String folder;

    private FileConfiguration configuration;

    public CustomFile(ClusterFactory plugin, String fileName, String folder) {
        this.plugin = plugin;

        this.strippedName = fileName.replace(".yml", "");
        this.fileName = fileName;

        this.folder = folder;

        File newFolder = new File(this.plugin.getDataFolder(), "/" + this.folder);

        if (!newFolder.exists()) {
            newFolder.mkdirs();

            if (this.plugin.isLogging()) this.plugin.getLogger().info("The folder " + folder + "/ was not found so it was created.");

            this.configuration = null;

            return;
        }

        File newFile = new File(this.plugin.getDataFolder(), this.folder + "/" + this.fileName);

        if (newFile.exists()) {
            this.configuration = CompletableFuture.supplyAsync(() -> YamlConfiguration.loadConfiguration(newFile)).join();

            return;
        }

        this.configuration = null;
    }

    public String getStrippedName() {
        return strippedName;
    }

    public String getFileName() {
        return fileName;
    }

    public String getFolder() {
        return folder;
    }

    public FileConfiguration getConfiguration() {
        return configuration;
    }

    public void setConfiguration(FileConfiguration configuration) {
        this.configuration = configuration;
    }

    public boolean exists() {
        return this.configuration != null;
    }

    public void save() {
        if (this.configuration == null) {
            if (this.plugin.isLogging()) this.plugin.getLogger().warning("The file configuration is null.");

            return;
        }

        try {
            File newFile = new File(this.plugin.getDataFolder(), this.folder + "/" + this.fileName);

            this.configuration.save(newFile);

            if (this.plugin.isLogging()) this.plugin.getLogger().info("Successfully saved " + this.fileName + ".");
        } catch (IOException exception) {
            this.plugin.getLogger().log(Level.SEVERE, "Could not save " + this.fileName + "!", exception);
        }
    }

    public void reload() {
        if (this.configuration == null) {
            if (this.plugin.isLogging()) this.plugin.getLogger().warning("The file configuration is null.");

            return;
        }

        try {
            File newFile = new File(this.plugin.getDataFolder(), this.folder + "/" + this.fileName);

            this.configuration = YamlConfiguration.loadConfiguration(newFile);

            if (this.plugin.isLogging()) this.plugin.getLogger().info("Successfully reloaded " + this.fileName + ".");
        } catch (Exception exception) {
            this.plugin.getLogger().log(Level.SEVERE, "Could not reload the " + this.fileName + "!", exception);
        }
    }
}