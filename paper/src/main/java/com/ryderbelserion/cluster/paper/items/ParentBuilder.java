package com.ryderbelserion.cluster.paper.items;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class ParentBuilder {

    public static ItemBuilder of(JavaPlugin plugin) {
        return new ItemBuilder(plugin);
    }

    public static ItemBuilder of(JavaPlugin plugin, ItemStack itemStack) {
        return new ItemBuilder(plugin, itemStack);
    }

    public static ItemBuilder of(JavaPlugin plugin, Material material) {
        return new ItemBuilder(plugin, new ItemStack(material));
    }
}