package com.ryderbelserion.cluster.paper.structures;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.structure.StructureManager;
import java.io.File;
import java.util.List;
import java.util.Set;

public interface IStructureManager {

    StructureManager getStructureManager();

    void saveStructure(File file, Location one, Location two, boolean includeEntities);

    void pasteStructure(Location location, boolean storeBlocks);

    void removeStructure();

    Set<Location> getBlocks(Location location);

    double getStructureX();

    double getStructureY();

    double getStructureZ();

    Set<Location> getNearbyBlocks();

    List<Material> getBlockBlacklist();

    void createStructure();

    File getStructureFile();

}