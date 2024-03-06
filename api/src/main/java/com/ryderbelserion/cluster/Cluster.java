package com.ryderbelserion.cluster;

import com.ryderbelserion.cluster.platform.ClusterServer;

public class Cluster {

    private final ClusterServer server;

    public Cluster(ClusterServer server) {
        this.server = server;

        // Register provider.
        ClusterProvider.register(this);
    }

    public void disable() {
        // Unregister provider.
        ClusterProvider.unregister();
    }

    public ClusterServer getServer() {
        return this.server;
    }
}