package com.ryderbelserion.cluster.bukkit.items;

import com.ryderbelserion.cluster.bukkit.BukkitPlugin;
import com.ryderbelserion.cluster.bukkit.api.adventure.FancyLogger;
import com.ryderbelserion.cluster.bukkit.api.registry.BukkitProvider;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public class ItemBuilder {

    private final @NotNull BukkitPlugin bukkitPlugin = BukkitProvider.get();

    private final @NotNull JavaPlugin plugin = this.bukkitPlugin.getPlugin();

    private final ItemStack itemStack;
    private final ItemMeta itemMeta;

    private final Material material;

    public ItemBuilder(ItemStack itemStack) {
        this.itemStack = itemStack;

        this.material = itemStack.getType();

        this.itemMeta = itemStack.hasItemMeta() ? itemStack.getItemMeta() : this.plugin.getServer().getItemFactory().getItemMeta(this.material);
    }

    public ItemStack build() {
        if (this.material != Material.AIR) {
            this.itemMeta.getPersistentDataContainer().set(new NamespacedKey(this.plugin, ""), PersistentDataType.STRING, "");
        } else {
            FancyLogger.warn("Material must be something other then AIR or not null.");
        }

        this.itemStack.setItemMeta(this.itemMeta);

        this.itemMeta.getPersistentDataContainer().getKeys().stream().toList().forEach(key -> FancyLogger.debug(key.getKey()));

        return this.itemStack;
    }
}