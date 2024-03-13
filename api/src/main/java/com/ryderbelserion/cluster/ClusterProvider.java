package com.ryderbelserion.cluster;

public final class ClusterProvider {

    private static Cluster instance;

    private ClusterProvider() {
        throw new UnsupportedOperationException("This class cannot be instantiated");
    }

    public static Cluster get() {
        if (instance == null) {
            throw new IllegalStateException("Cluster is not loaded.");
        }

        return instance;
    }

    static void register(final Cluster instance) {
        if (ClusterProvider.instance != null) {
            instance.getServer().getLogger().warning("Cluster is already enabled.");
            return;
        }

        ClusterProvider.instance = instance;
    }

    static void unregister() {
        ClusterProvider.instance = null;
    }
}