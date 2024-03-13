package com.ryderbelserion.cluster.platform;

import com.ryderbelserion.cluster.ClusterPackage;
import java.io.File;
import java.util.logging.Logger;

public class ClusterPaperServer implements ClusterServer {

    private final ClusterPackage instance;

    public ClusterPaperServer(ClusterPackage instance) {
        this.instance = instance;
    }

    @Override
    public boolean isLogging() {
        return this.instance.isLogging();
    }

    @Override
    public boolean isPapiEnabled() {
        return this.instance.isPapiEnabled();
    }

    @Override
    public boolean isOraxenEnabled() {
        return this.instance.isOraxenEnabled();
    }

    @Override
    public boolean isItemsAdderEnabled() {
        return this.instance.isItemsAdderEnabled();
    }

    @Override
    public void saveResource(String resourcePath, boolean replace) {
        this.instance.saveResource(resourcePath, replace);
    }

    @Override
    public Logger getLogger() {
        return this.instance.getLogger();
    }

    @Override
    public File getFolder() {
        return this.instance.getFolder();
    }

    @Override
    public boolean isHeadDatabaseEnabled() {
        return this.instance.isHeadDatabaseEnabled();
    }
}