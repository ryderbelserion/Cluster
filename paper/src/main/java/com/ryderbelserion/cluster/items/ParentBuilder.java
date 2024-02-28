package com.ryderbelserion.cluster.items;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class ParentBuilder extends ItemBuilder {

    public ParentBuilder() {}

    public ParentBuilder(ItemStack itemStack) {
        super(itemStack);
    }

    public ParentBuilder(ItemBuilder itemBuilder) {
        super(itemBuilder);
    }

    public static ItemBuilder of() {
        return new ParentBuilder();
    }

    public static ItemBuilder of(ItemStack itemStack) {
        return new ParentBuilder(itemStack);
    }

    public static ItemBuilder of(ItemBuilder itemBuilder) {
        return new ParentBuilder(itemBuilder);
    }

    public static ItemBuilder of(Material material) {
        return new ParentBuilder(new ItemStack(material));
    }
}