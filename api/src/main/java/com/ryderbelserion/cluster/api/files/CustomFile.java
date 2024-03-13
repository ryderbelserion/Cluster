package com.ryderbelserion.cluster.api.files;

import com.ryderbelserion.cluster.Cluster;
import com.ryderbelserion.cluster.ClusterProvider;
import com.ryderbelserion.cluster.platform.ClusterServer;
import org.simpleyaml.configuration.file.FileConfiguration;
import org.simpleyaml.configuration.file.YamlConfiguration;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CustomFile {

    private final Cluster provider = ClusterProvider.get();

    private final ClusterServer server = this.provider.getServer();

    private final boolean isLogging = this.server.isLogging();
    private final File dataFolder = this.server.getFolder();
    private final Logger logger = this.server.getLogger();

    private final String strippedName;
    private final String fileName;
    private final String folder;

    private YamlConfiguration configuration;

    public CustomFile(String fileName, String folder) {
        this.strippedName = fileName.replace(".yml", "");
        this.fileName = fileName;

        this.folder = folder;

        File newFolder = new File(this.dataFolder, "/" + this.folder);

        if (!newFolder.exists()) {
            newFolder.mkdirs();

            if (this.isLogging) this.logger.info("The folder " + folder + "/ was not found so it was created.");

            this.configuration = null;

            return;
        }

        File newFile = new File(this.dataFolder, this.folder + "/" + this.fileName);

        if (newFile.exists()) {
            this.configuration = CompletableFuture.supplyAsync(() -> {
                try {
                    return YamlConfiguration.loadConfiguration(newFile);
                } catch (IOException exception) {
                    this.logger.log(Level.WARNING, "Failed to load " + newFile.getName() + ".", exception);
                }

                return null;
            }).join();

            return;
        }

        this.configuration = null;
    }

    public String getStrippedName() {
        return this.strippedName;
    }

    public String getFileName() {
        return this.fileName;
    }

    public String getFolder() {
        return this.folder;
    }

    public FileConfiguration getConfiguration() {
        return this.configuration;
    }

    public void setConfiguration(YamlConfiguration configuration) {
        this.configuration = configuration;
    }

    public boolean exists() {
        return this.configuration != null;
    }

    public void save() {
        if (this.configuration == null) {
            if (this.isLogging) this.logger.warning("The file configuration is null.");

            return;
        }

        try {
            File newFile = new File(this.dataFolder, this.folder + "/" + this.fileName);

            this.configuration.save(newFile);

            if (this.isLogging) this.logger.info("Successfully saved " + this.fileName + ".");
        } catch (IOException exception) {
            this.logger.log(Level.SEVERE, "Could not save " + this.fileName + "!", exception);
        }
    }

    public void reload() {
        if (this.configuration == null) {
            if (this.isLogging) this.logger.warning("The file configuration is null.");

            return;
        }

        try {
            File newFile = new File(this.dataFolder, this.folder + "/" + this.fileName);

            this.configuration = YamlConfiguration.loadConfiguration(newFile);

            if (this.isLogging) this.logger.info("Successfully reloaded " + this.fileName + ".");
        } catch (Exception exception) {
            this.logger.log(Level.SEVERE, "Could not reload the " + this.fileName + "!", exception);
        }
    }
}