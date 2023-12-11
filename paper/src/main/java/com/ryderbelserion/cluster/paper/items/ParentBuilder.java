package com.ryderbelserion.cluster.paper.items;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class ParentBuilder {

    public static ItemBuilder of() {
        return new ItemBuilder();
    }

    public static ItemBuilder of(ItemStack itemStack) {
        return new ItemBuilder(itemStack);
    }

    public static ItemBuilder of(ItemBuilder itemBuilder) {
        return new ItemBuilder(itemBuilder);
    }

    public static ItemBuilder of(Material material) {
        return new ItemBuilder(new ItemStack(material));
    }
}