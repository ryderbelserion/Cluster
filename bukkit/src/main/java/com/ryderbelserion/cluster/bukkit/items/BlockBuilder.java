package com.ryderbelserion.cluster.bukkit.items;

import com.ryderbelserion.cluster.bukkit.api.adventure.FancyLogger;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;

public class BlockBuilder {

    private final Block block;
    private final BlockData blockData;
    private final Material material;

    protected BlockBuilder(Block block) {
        this.block = block;

        this.blockData = block.getBlockData();

        this.material = block.getType();
    }

    public Block build() {
        if (!isAir()) {

        } else {
            FancyLogger.warn("Material cannot be air or null.");
        }

        this.block.setBlockData(this.blockData);

        return this.block;
    }

    private boolean isAir() {
        return this.material.isAir();
    }
}