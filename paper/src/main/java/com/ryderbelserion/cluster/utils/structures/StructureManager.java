package com.ryderbelserion.cluster.utils.structures;

import com.google.common.collect.Lists;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.structure.Mirror;
import org.bukkit.block.structure.StructureRotation;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.structure.Structure;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;

public class StructureManager implements IStructureManager {

    private final JavaPlugin plugin;
    private final File file;

    private final HashSet<Location> postStructurePasteBlocks = new HashSet<>();
    private final HashSet<Location> preStructurePasteBlocks = new HashSet<>();

    private Structure structure;

    public StructureManager(JavaPlugin plugin, File file) {
        this.plugin = plugin;

        this.file = file;

        this.structure = CompletableFuture.supplyAsync(() -> {
            try {
                return this.plugin.getServer().getStructureManager().loadStructure(this.file);
            } catch (IOException exception) {
                this.plugin.getLogger().log(Level.SEVERE, "Failed to load structure: " + this.file.getName() + "!", exception);

                return null;
            }
        }).join();
    }

    public StructureManager(JavaPlugin plugin) {
        this.plugin = plugin;

        // We don't need this as we don't have a structure.
        this.structure = null;
        this.file = null;
    }

    @Override
    public org.bukkit.structure.StructureManager getStructureManager() {
        return this.plugin.getServer().getStructureManager();
    }

    @Override
    public void saveStructure(File file, Location one, Location two, boolean includeEntities) {
        // Fill the structure with blocks between 2 corners.
        this.structure.fill(one, two, includeEntities);

        // Save structure to file.
        try {
            getStructureManager().saveStructure(file, this.structure);
        } catch (IOException exception) {
            this.plugin.getLogger().log(Level.SEVERE, "Failed to save structure to " + file.getName() + "!", exception);
        }
    }

    @Override
    public void pasteStructure(Location location, boolean storeBlocks) {
        try {
            // Get the blocks from the hashset and set them.
            if (storeBlocks) getBlocks(location);

            // Place the structure.
            this.structure.place(location, false, StructureRotation.NONE, Mirror.NONE, 0, 1F, new Random());

            // Get the structure blocks.
            if (storeBlocks) getStructureBlocks(location);
        } catch (Exception exception) {
            this.plugin.getLogger().log(Level.SEVERE, "Could not paste structure", exception);
        }
    }

    @Override
    public void removeStructure() {
        this.postStructurePasteBlocks.forEach(block -> {
            if (block.getBlock().getType() != Material.AIR) {
                Location location = block.toBlockLocation();

                location.getBlock().setType(Material.AIR, true);
            }
        });
    }

    private void getStructureBlocks(Location location) {
        for (int x = 0; x < getStructureX(); x++) {
            for (int y = 0; y < getStructureY(); y++) {
                for (int z = 0; z < getStructureZ(); z++) {
                    Block relativeLocation = location.getBlock().getRelative(x, y, z);

                    this.postStructurePasteBlocks.add(relativeLocation.getLocation());

                    this.postStructurePasteBlocks.forEach(block -> {
                        Location blockLoc = block.toBlockLocation();

                        blockLoc.getBlock().getState().update();
                    });
                }
            }
        }
    }

    @Override
    public Set<Location> getBlocks(Location location) {
        for (int x = 0; x < getStructureX(); x++) {
            for (int y = 0; y < getStructureY(); y++) {
                for (int z = 0; z < getStructureZ(); z++) {
                    Block relativeLocation = location.getBlock().getRelative(x, y, z).getLocation().subtract(2, 0.0, 2).getBlock();

                    this.preStructurePasteBlocks.add(relativeLocation.getLocation());
                }
            }
        }

        return getNearbyBlocks();
    }

    @Override
    public double getStructureX() {
        return this.structure.getSize().getX();
    }

    @Override
    public double getStructureY() {
        return this.structure.getSize().getY();
    }

    @Override
    public double getStructureZ() {
        return this.structure.getSize().getZ();
    }

    @Override
    public Set<Location> getNearbyBlocks() {
        return Collections.unmodifiableSet(this.preStructurePasteBlocks);
    }

    @Override
    public List<Material> getBlockBlacklist() {
        return Lists.newArrayList(Material.ACACIA_SIGN, Material.BIRCH_SIGN, Material.DARK_OAK_SIGN, Material.JUNGLE_SIGN, Material.OAK_SIGN,
                Material.SPRUCE_SIGN, Material.ACACIA_WALL_SIGN, Material.BIRCH_WALL_SIGN, Material.DARK_OAK_WALL_SIGN, Material.JUNGLE_WALL_SIGN, Material.OAK_WALL_SIGN,
                Material.SPRUCE_WALL_SIGN,Material.STONE_BUTTON,Material.BIRCH_BUTTON,Material.ACACIA_BUTTON,Material.DARK_OAK_BUTTON, Material.JUNGLE_BUTTON, Material.SPRUCE_BUTTON);
    }

    @Override
    public void createStructure() {
        this.structure = getStructureManager().createStructure();
    }

    @Override
    public File getStructureFile() {
        return this.file;
    }
}