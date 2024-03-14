package com.ryderbelserion.cluster;

import java.io.File;
import java.util.logging.Logger;

public abstract class Cluster {

    public Cluster() {
        // Register provider.
        ClusterProvider.register(this);
    }

    public void disable() {
        // Unregister provider.
        ClusterProvider.unregister();
    }

    public abstract boolean isLogging();

    public abstract boolean isPapiEnabled();

    public abstract boolean isOraxenEnabled();

    public abstract void saveResource(String file, boolean overwrite);

    public abstract Logger getLogger();

    public abstract File getFolder();
}