package com.ryderbelserion.cluster.platform;

import java.io.File;
import java.util.logging.Logger;

public interface ClusterServer {

    void setLogging(boolean value);

    boolean isLogging();

    boolean isPapiEnabled();

    boolean isOraxenEnabled();

    boolean isItemsAdderEnabled();

    void saveResource(String resourcePath, boolean replace);

    Logger getLogger();

    File getFolder();

    boolean isHeadDatabaseEnabled();

}