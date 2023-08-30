package com.ryderbelserion.cluster.bukkit.items;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class ItemBuilder extends BaseItemBuilder<ItemBuilder> {

    protected ItemBuilder(ItemStack itemStack) {
        super(itemStack);
    }

    public static ItemBuilder of(ItemStack itemStack) {
        return new ItemBuilder(itemStack);
    }

    public static ItemBuilder of(Material material) {
        return new ItemBuilder(new ItemStack(material));
    }
}