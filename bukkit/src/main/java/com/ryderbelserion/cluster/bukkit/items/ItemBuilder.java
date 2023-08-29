package com.ryderbelserion.cluster.bukkit.items;

import com.ryderbelserion.cluster.bukkit.BukkitPlugin;
import com.ryderbelserion.cluster.bukkit.api.adventure.FancyLogger;
import com.ryderbelserion.cluster.bukkit.api.registry.BukkitProvider;
import net.minecraft.nbt.CompoundTag;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.craftbukkit.v1_20_R1.inventory.CraftItemStack;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import java.util.HashMap;

public class ItemBuilder {

    private final @NotNull BukkitPlugin bukkitPlugin = BukkitProvider.get();

    private final @NotNull JavaPlugin plugin = this.bukkitPlugin.getPlugin();

    private ItemStack itemStack;
    private ItemMeta itemMeta;

    private final Material material;

    private String itemData;

    public ItemBuilder(ItemStack itemStack) {
        this.itemStack = itemStack;

        this.material = itemStack.getType();

        this.itemMeta = itemStack.hasItemMeta() ? itemStack.getItemMeta() : this.plugin.getServer().getItemFactory().getItemMeta(this.material);
    }

    public ItemStack build() {
        if (!isAir()) {
            if (!this.itemData.isEmpty()) {
                net.minecraft.world.item.ItemStack nmsItem = CraftItemStack.asNMSCopy(this.itemStack);

                CompoundTag tag = new CompoundTag();

                tag.putString("item-data", this.itemData);

                nmsItem.setTag(tag);

                ItemStack newStack = CraftItemStack.asBukkitCopy(nmsItem);

                this.itemMeta = newStack.getItemMeta();
                this.itemStack = newStack;

                return this.itemStack;
            }
        } else {
            FancyLogger.warn("Material cannot be air.");
        }

        this.itemStack.setItemMeta(this.itemMeta);

        return this.itemStack;
    }

    private boolean isAir() {
        return this.material.isAir();
    }

    public ItemBuilder setItemData(String itemData) {
        if (itemData.isBlank() || itemData.isEmpty()) return this;

        this.itemData = itemData;

        return this;
    }

    // Enchantments
    public ItemBuilder addEnchantments(HashMap<Enchantment, Integer> enchantments, boolean unsafeEnchantments) {
        enchantments.forEach((enchantment, level) -> addEnchantment(enchantment, level, unsafeEnchantments));
        return this;
    }

    public ItemBuilder addEnchantment(Enchantment enchantment, int level, boolean unsafeEnchantments) {
        this.itemMeta.addEnchant(enchantment, level, unsafeEnchantments);
        return this;
    }

    public ItemBuilder removeEnchantment(Enchantment enchantment) {
        this.itemMeta.removeEnchant(enchantment);
        return this;
    }
}