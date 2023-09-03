package com.ryderbelserion.cluster.bukkit.items;

import com.ryderbelserion.cluster.api.adventure.FancyLogger;
import org.bukkit.Material;
import org.bukkit.block.Block;

public class BlockBuilder {

    private final Block block;
    private final Material material;

    protected BlockBuilder(Block block) {
        this.block = block;

        this.material = block.getType();
    }

    public Block build() {
        if (!isAir()) {

        } else {
            FancyLogger.warn("Material cannot be air or null.");
        }

        return getBlock();
    }

    private boolean isAir() {
        return this.material.isAir();
    }

    private Block getBlock() {
        return this.block;
    }

    private Material getMaterial() {
        return this.material;
    }
}