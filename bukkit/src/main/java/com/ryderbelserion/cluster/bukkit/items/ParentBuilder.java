package com.ryderbelserion.cluster.bukkit.items;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

public class ParentBuilder {

    public static ItemBuilder of() {
        return new ItemBuilder();
    }

    public static ItemBuilder of(ItemStack itemStack, boolean isLegacy) {
        return new ItemBuilder(itemStack, isLegacy);
    }

    public static ItemBuilder of(Material material, boolean isLegacy) {
        return new ItemBuilder(new ItemStack(material), isLegacy);
    }

    public static BlockBuilder of(Block block) {
        return new BlockBuilder(block);
    }
}