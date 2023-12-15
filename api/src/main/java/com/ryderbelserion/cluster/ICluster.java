package com.ryderbelserion.cluster;

import java.io.File;
import java.util.logging.Logger;

public interface ICluster {

    File getDataFolder();

    Logger getLogger();

    boolean isLogging();

}