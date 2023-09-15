package com.ryderbelserion.cluster.bukkit.items;

import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

public class NbtBuilder {

    private final JavaPlugin plugin;

    public NbtBuilder(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public ItemStack setString(ItemStack itemStack, String key, String value) {
        itemStack.editMeta(meta -> meta.getPersistentDataContainer().set(new NamespacedKey(this.plugin, key), PersistentDataType.STRING, value));

        return itemStack;
    }

    public String getString(ItemStack itemStack, String key) {
        ItemMeta meta = itemStack.getItemMeta();

        if (meta == null) return null;

        return meta.getPersistentDataContainer().get(new NamespacedKey(this.plugin, key), PersistentDataType.STRING);
    }

    public ItemStack setBoolean(ItemStack itemStack, String key, boolean value) {
        itemStack.editMeta(meta -> meta.getPersistentDataContainer().set(new NamespacedKey(this.plugin, key), PersistentDataType.BOOLEAN, value));

        return itemStack;
    }

    public ItemStack removeTag(ItemStack itemStack, String key) {
        itemStack.editMeta(meta -> meta.getPersistentDataContainer().remove(new NamespacedKey(this.plugin, key)));

        return itemStack;
    }
}