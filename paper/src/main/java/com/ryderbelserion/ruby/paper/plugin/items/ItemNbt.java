package com.ryderbelserion.ruby.paper.plugin.items;

import com.ryderbelserion.ruby.paper.PaperPlugin;
import com.ryderbelserion.ruby.paper.plugin.registry.PaperProvider;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public class ItemNbt {

    private final @NotNull PaperPlugin paperPlugin = PaperProvider.get();

    private final @NotNull JavaPlugin plugin = this.paperPlugin.getPlugin();

    public ItemStack setString(ItemStack itemStack, String key, String value) {
        ItemMeta meta = itemStack.getItemMeta();

        if (meta == null) return null;

        meta.getPersistentDataContainer().set(new NamespacedKey(this.plugin, key), PersistentDataType.STRING, value);
        itemStack.setItemMeta(meta);

        return itemStack;
    }

    public String getString(ItemStack itemStack, String key) {
        ItemMeta meta = itemStack.getItemMeta();

        if (meta == null) return null;

        return meta.getPersistentDataContainer().get(new NamespacedKey(this.plugin, key), PersistentDataType.STRING);
    }

    public ItemStack setBoolean(ItemStack itemStack, String key, boolean value) {
        ItemMeta meta = itemStack.getItemMeta();

        if (meta == null) return null;

        meta.getPersistentDataContainer().set(new NamespacedKey(this.plugin, key), PersistentDataType.BOOLEAN, value);
        itemStack.setItemMeta(meta);

        return itemStack;
    }

    public ItemStack removeTag(ItemStack itemStack, String key) {
        ItemMeta meta = itemStack.getItemMeta();

        if (meta == null) return null;

        meta.getPersistentDataContainer().remove(new NamespacedKey(this.plugin, key));
        itemStack.setItemMeta(meta);

        return itemStack;
    }
}