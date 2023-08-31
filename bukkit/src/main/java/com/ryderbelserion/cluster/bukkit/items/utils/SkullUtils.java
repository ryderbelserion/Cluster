package com.ryderbelserion.cluster.bukkit.items.utils;

import com.ryderbelserion.cluster.bukkit.BukkitPlugin;
import com.ryderbelserion.cluster.bukkit.api.registry.BukkitProvider;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Skull;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Base64;
import java.util.UUID;

public class SkullUtils {

    private static final @NotNull BukkitPlugin provider = BukkitProvider.get();

    private static final @NotNull JavaPlugin plugin = provider.getPlugin();

    /**
     * Creates a player skull based on a player's name.
     *
     * @param name The Player's name
     * @return The head of the Player
     */
    public static ItemStack itemFromName(String name) {
        ItemStack item = getPlayerSkullItem();

        return itemWithName(item, name);
    }

    /**
     * Creates a player skull based on a player's name.
     *
     * @param item The item to apply the name to
     * @param name The Player's name
     * @return The head of the Player
     */
    public static ItemStack itemWithName(ItemStack item, String name) {
        notNull(item, "item");
        notNull(name, "name");

        return plugin.getServer().getUnsafe().modifyItemStack(item,
                "{SkullOwner:\"" + name + "\"}"
        );
    }

    /**
     * Creates a player skull with a UUID. 1.13 only.
     *
     * @param uuid The Player's UUID
     * @return The head of the Player
     */
    public static ItemStack itemFromUuid(UUID uuid) {
        ItemStack item = getPlayerSkullItem();

        return itemWithUuid(item, uuid);
    }

    /**
     * Creates a player skull based on a UUID. 1.13 only.
     *
     * @param item The item to apply the name to
     * @param uuid The Player's UUID
     * @return The head of the Player
     */
    public static ItemStack itemWithUuid(ItemStack item, UUID uuid) {
        notNull(item, "item");
        notNull(uuid, "id");

        SkullMeta meta = (SkullMeta) item.getItemMeta();
        meta.setOwningPlayer(plugin.getServer().getOfflinePlayer(uuid));
        item.setItemMeta(meta);

        return item;
    }

    /**
     * Creates a player skull based on a Mojang server URL.
     *
     * @param url The URL of the Mojang skin
     * @return The head associated with the URL
     */
    public static ItemStack itemFromUrl(String url) {
        ItemStack item = getPlayerSkullItem();

        return itemWithUrl(item, url);
    }

    /**
     * Creates a player skull based on a Mojang server URL.
     *
     * @param item The item to apply the skin to
     * @param url The URL of the Mojang skin
     * @return The head associated with the URL
     */
    public static ItemStack itemWithUrl(ItemStack item, String url) {
        notNull(item, "item");
        notNull(url, "url");

        return itemWithBase64(item, urlToBase64(url));
    }

    /**
     * Creates a player skull based on a base64 string containing the link to the skin.
     *
     * @param base64 The base64 string containing the texture
     * @return The head with a custom texture
     */
    public static ItemStack itemFromBase64(String base64) {
        ItemStack item = getPlayerSkullItem();

        return itemWithBase64(item, base64);
    }

    /**
     * Applies the base64 string to the ItemStack.
     *
     * @param item The ItemStack to put the base64 onto
     * @param base64 The base64 string containing the texture
     * @return The head with a custom texture
     */
    public static ItemStack itemWithBase64(ItemStack item, String base64) {
        notNull(item, "item");
        notNull(base64, "base64");

        UUID hashAsId = new UUID(base64.hashCode(), base64.hashCode());
        return plugin.getServer().getUnsafe().modifyItemStack(item,
                "{SkullOwner:{Id:\"" + hashAsId + "\",Properties:{textures:[{Value:\"" + base64 + "\"}]}}}"
        );
    }

    /**
     * Sets the block to a skull with the given UUID.
     *
     * @param block The block to set
     * @param uuid The player to set it to
     */
    public static void blockWithUuid(Block block, UUID uuid) {
        notNull(block, "block");
        notNull(uuid, "id");

        setBlockType(block);

        ((Skull) block.getState()).setOwningPlayer(plugin.getServer().getOfflinePlayer(uuid));
    }

    /**
     * Sets the block to a skull with the given UUID.
     *
     * @param block The block to set
     * @param url The mojang URL to set it to use
     */
    public static void blockWithUrl(Block block, String url) {
        notNull(block, "block");
        notNull(url, "url");

        blockWithBase64(block, urlToBase64(url));
    }

    /**
     * Sets the block to a skull with the given UUID.
     *
     * @param block The block to set
     * @param base64 The base64 to set it to use
     */
    public static void blockWithBase64(Block block, String base64) {
        notNull(block, "block");
        notNull(base64, "base64");

        UUID hashAsId = new UUID(base64.hashCode(), base64.hashCode());

        String args = String.format(
                "%d %d %d %s",
                block.getX(),
                block.getY(),
                block.getZ(),
                "{Owner:{Id:\"" + hashAsId + "\",Properties:{textures:[{Value:\"" + base64 + "\"}]}}}"
        );

        plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), "data merge block " + args);
    }

    private static ItemStack getPlayerSkullItem() {
        return new ItemStack(Material.valueOf("PLAYER_HEAD"));
    }

    private static void setBlockType(Block block) {
        block.setType(Material.valueOf("PLAYER_HEAD"), false);
    }

    private static void notNull(Object instance, String name) {
        if (instance == null) throw new NullPointerException(name + " should not be null!");
    }

    private static String urlToBase64(String url) {
        URI actualUrl;

        try {
            actualUrl = new URI(url);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }

        String toEncode = "{\"textures\":{\"SKIN\":{\"url\":\"" + actualUrl + "\"}}}";
        return Base64.getEncoder().encodeToString(toEncode.getBytes());
    }
}