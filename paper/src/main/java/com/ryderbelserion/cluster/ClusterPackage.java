package com.ryderbelserion.cluster;

import com.ryderbelserion.cluster.api.files.FileManager;
import com.ryderbelserion.cluster.platform.ClusterPaperServer;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public class ClusterPackage {

    private final FileManager fileManager;
    private final Cluster cluster;

    /**
     * Initializes the framework.
     *
     * @param plugin the instance of the plugin using the framework.
     */
    public ClusterPackage(JavaPlugin plugin) {
        this.cluster = new Cluster(new ClusterPaperServer(plugin));

        this.fileManager = new FileManager();
    }

    /**
     * @return instance of the FileManager.
     */
    @NotNull
    public FileManager getFileManager() {
        return this.fileManager;
    }

    @NotNull
    public Cluster getCluster() {
        return this.cluster;
    }
}