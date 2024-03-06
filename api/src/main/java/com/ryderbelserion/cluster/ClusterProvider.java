package com.ryderbelserion.cluster;

public final class ClusterProvider {

    private static Cluster instance;

    private ClusterProvider() {
        throw new UnsupportedOperationException("This class cannot be instantiated");
    }

    public static Cluster get() {
        if (instance == null) {
            throw new IllegalStateException("CrazyAuctions is not loaded.");
        }

        return instance;
    }

    static void register(final Cluster instance) {
        if (get() != null) {
            return;
        }

        ClusterProvider.instance = instance;
    }

    static void unregister() {
        ClusterProvider.instance = null;
    }
}