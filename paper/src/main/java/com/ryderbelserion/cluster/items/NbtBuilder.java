package com.ryderbelserion.cluster.items;

import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

public class NbtBuilder {

    private final JavaPlugin plugin;
    private final ItemStack itemStack;

    public NbtBuilder(JavaPlugin plugin, ItemStack itemStack) {
        this.plugin = plugin;

        this.itemStack = itemStack;
    }

    public ItemStack setString(String key, String value) {
        this.itemStack.editMeta(meta -> meta.getPersistentDataContainer().set(new NamespacedKey(this.plugin, key), PersistentDataType.STRING, value));

        return this.itemStack;
    }

    public boolean hasString(String key) {
        ItemMeta meta = this.itemStack.getItemMeta();

        if (meta == null) return false;

        return meta.getPersistentDataContainer().has(new NamespacedKey(this.plugin, key));
    }

    public String getString(String key) {
        ItemMeta meta = this.itemStack.getItemMeta();

        if (meta == null) return null;

        return meta.getPersistentDataContainer().get(new NamespacedKey(this.plugin, key), PersistentDataType.STRING);
    }

    public ItemStack setBoolean(String key, boolean value) {
        this.itemStack.editMeta(meta -> meta.getPersistentDataContainer().set(new NamespacedKey(this.plugin, key), PersistentDataType.BOOLEAN, value));

        return this.itemStack;
    }

    public ItemStack removeTag(String key) {
        this.itemStack.editMeta(meta -> meta.getPersistentDataContainer().remove(new NamespacedKey(this.plugin, key)));

        return this.itemStack;
    }

    public ItemStack getItemStack() {
        return this.itemStack;
    }
}