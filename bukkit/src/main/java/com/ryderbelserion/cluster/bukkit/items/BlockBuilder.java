package com.ryderbelserion.cluster.bukkit.items;

import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.jetbrains.annotations.NotNull;

public class BlockBuilder {

    private final Block block;

    protected BlockBuilder(Block block) {
        this.block = block;
    }

    public void build() {

    }

    public BlockBuilder setBlock(Material material) {
        getBlock().setType(material);

        return this;
    }

    public BlockBuilder setBlock(Material material, boolean applyPhysics) {
        getBlock().setType(material, applyPhysics);

        return this;
    }

    public BlockBuilder setBiome(Biome biome) {
        getBlock().setBiome(biome);

        return this;
    }

    private @NotNull Block getBlock() {
        return this.block;
    }
}