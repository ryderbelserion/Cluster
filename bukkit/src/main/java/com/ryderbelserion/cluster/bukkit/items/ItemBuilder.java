package com.ryderbelserion.cluster.bukkit.items;

import com.destroystokyo.paper.profile.PlayerProfile;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.ryderbelserion.cluster.bukkit.BukkitPlugin;
import com.ryderbelserion.cluster.bukkit.api.adventure.FancyLogger;
import com.ryderbelserion.cluster.bukkit.api.registry.BukkitProvider;
import com.ryderbelserion.cluster.bukkit.api.utils.ColorUtils;
import com.ryderbelserion.cluster.bukkit.items.utils.DyeUtils;
import net.kyori.adventure.text.Component;
import net.minecraft.nbt.TagParser;
import org.bukkit.*;
import org.bukkit.block.banner.Pattern;
import org.bukkit.craftbukkit.v1_20_R1.inventory.CraftItemStack;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.*;
import org.bukkit.inventory.meta.trim.ArmorTrim;
import org.bukkit.inventory.meta.trim.TrimMaterial;
import org.bukkit.inventory.meta.trim.TrimPattern;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;
import org.bukkit.profile.PlayerTextures;
import org.jetbrains.annotations.NotNull;
import java.util.*;
import java.util.concurrent.CompletableFuture;

@SuppressWarnings("ALL")
public class ItemBuilder {

    private final @NotNull BukkitPlugin provider = BukkitProvider.get();

    private final @NotNull JavaPlugin plugin = this.provider.getPlugin();

    // Items
    private Material material = Material.STONE;
    private ItemStack itemStack = new ItemStack(this.material);

    // NBT Data
    private String itemData = "";

    // Display
    private Component displayName = Component.empty();
    private List<Component> displayLore = Collections.emptyList();
    private int itemDamage = this.material.getMaxDurability();

    // Model Data
    private boolean hasCustomModelData = false;
    private int customModelData = 0;

    // Potions
    private boolean isPotion = false;
    private Color potionColor = Color.RED;
    private PotionEffectType potionType = null;

    // Player Heads
    private String player = "";
    private boolean isPlayer = false;
    private boolean isHead = false;

    // Arrows
    private boolean isTippedArrow = false;

    // Armor
    private boolean isLeatherArmor = false;
    private boolean isArmor = false;

    // Trims
    private TrimMaterial trimMaterial = null;
    private TrimPattern trimPattern = null;
    private Color armorColor = Color.RED;

    // Banners
    private boolean isBanner = false;
    private List<Pattern> patterns = Collections.emptyList();

    // Shields
    private boolean isShield = false;

    // Maps
    private boolean isMap = false;
    private Color mapColor = Color.RED;

    // Enchantments or ItemFlags
    private boolean isUnbreakable = false;

    private boolean hideItemFlags = false;
    private List<ItemFlag> itemFlags = Collections.emptyList();

    private boolean isGlowing = false;

    public ItemBuilder(ItemStack itemStack) {
        this.itemStack = itemStack;

        this.material = itemStack.getType();

        switch (this.material) {
            case LEATHER_HELMET, LEATHER_CHESTPLATE, LEATHER_LEGGINGS, LEATHER_BOOTS, LEATHER_HORSE_ARMOR -> this.isLeatherArmor = true;
            case POTION, SPLASH_POTION, LINGERING_POTION -> this.isPotion = true;
            case TIPPED_ARROW -> this.isTippedArrow = true;
            case FILLED_MAP -> this.isShield = true;
            case SHIELD -> this.isShield = true;
        }

        String name = this.material.name();

        this.isArmor = name.endsWith("_HELMET") || name.endsWith("_CHESTPLATE") || name.endsWith("_LEGGINGS") || name.endsWith("_BOOTS");

        this.isBanner = name.endsWith("BANNER");
    }

    public ItemBuilder() {}

    public ItemStack build() {
        if (!isAir()) {
            // If item data is not empty. We ignore all other options and simply return.
            if (!this.itemData.isEmpty()) {
                net.minecraft.world.item.ItemStack nmsItem = CraftItemStack.asNMSCopy(getItemStack());

                try {
                    nmsItem.setTag(TagParser.parseTag(this.itemData));
                } catch (CommandSyntaxException e) {
                    e.printStackTrace();
                }

                return CraftItemStack.asBukkitCopy(nmsItem);
            }

            getItemStack().editMeta(meta -> {
                // Set the display name.
                meta.displayName(this.displayName);
                // Set the lore.
                meta.lore(this.displayLore);

                // If the item is able to be damaged.
                if (meta instanceof Damageable damageable) {
                    if (this.itemDamage >= 1) {
                        if (this.itemDamage >= this.material.getMaxDurability()) {
                            damageable.setDamage(this.material.getMaxDurability());
                        } else {
                            damageable.setDamage(this.itemDamage);
                        }
                    }
                }

                if (this.isArmor) {
                    if (this.trimPattern != null && this.trimMaterial != null) {
                        ArmorMeta armorMeta = (ArmorMeta) meta;

                        armorMeta.setTrim(new ArmorTrim(this.trimMaterial, this.trimPattern));
                    }
                }

                if (this.isMap) {
                    MapMeta mapMeta = (MapMeta) meta;

                    mapMeta.setScaling(true);

                    if (this.mapColor != null) mapMeta.setColor(this.mapColor);
                }

                // Check if is potion and apply potion related settings.
                if (this.isPotion || this.isTippedArrow) {
                    PotionMeta potionMeta = (PotionMeta) meta;

                    // Single potion effect.
                    if (this.potionType != null) {
                        PotionEffect effect = new PotionEffect(this.potionType, -1, 1);

                        potionMeta.addCustomEffect(effect, true);

                        potionMeta.setBasePotionData(new PotionData(PotionType.valueOf(effect.getType().getName())));
                    }

                    if (this.potionColor != null) {
                        potionMeta.setColor(this.potionColor);
                    }
                }

                // If the item has model data.
                if (this.hasCustomModelData) {
                    meta.setCustomModelData(this.customModelData);
                }
            });
        } else {
            FancyLogger.warn("Material cannot be air or null.");
        }

        return getItemStack();
    }

    private boolean isAir() {
        return this.material.isAir();
    }

    private ItemStack getItemStack() {
        return this.itemStack;
    }

    // Name
    public ItemBuilder setDisplayName(String displayName) {
        this.displayName = ColorUtils.parse(displayName);

        return this;
    }

    public ItemBuilder setDisplayLore(List<String> displayLore) {
        displayLore.forEach(line -> {
            this.displayLore.add(ColorUtils.parse(line));
        });

        return this;
    }

    public void setItemData(String itemData) {
        this.itemData = itemData;
    }

    // Set the material type.
    public ItemBuilder setValue(String material) {
        if (material == null || material.isEmpty()) {
            List.of(
                    "Material cannot be null or empty, Output: " + material + ".",
                    "Please take a screenshot of this before asking for support."
            ).forEach(FancyLogger::warn);

            return this;
        }

        String metaData;

        if (material.contains(":")) {
            String[] section = material.split(":");

            material = section[0];
            metaData = section[1];

            if (metaData.contains("#")) {
                String modelData = metaData.split("#")[1];

                if (isValidInteger(modelData)) {
                    this.hasCustomModelData = true;
                    this.customModelData = Integer.parseInt(modelData);
                }
            }

            metaData = metaData.replace("#" + customModelData, "");

            if (isValidInteger(metaData)) {
                this.itemDamage = Integer.parseInt(metaData);
            } else {
                try {
                    PotionEffectType potionType = Registry.POTION_EFFECT_TYPE.get(NamespacedKey.minecraft(metaData));

                    this.potionType = potionType;
                } catch (Exception exception) {
                    FancyLogger.warn("Failed to set potion type " + metaData + ".");
                    FancyLogger.debug(exception.getMessage());
                }

                this.potionColor = DyeUtils.getColor(metaData);
            }
        }

        Material matchedMaterial = Material.matchMaterial(material);

        if (matchedMaterial != null) {
            this.itemStack = new ItemStack(matchedMaterial);

            this.material = this.itemStack.getType();
        }

        switch (this.material) {
            case LEATHER_HELMET, LEATHER_CHESTPLATE, LEATHER_LEGGINGS, LEATHER_BOOTS, LEATHER_HORSE_ARMOR -> this.isLeatherArmor = true;
            case POTION, SPLASH_POTION, LINGERING_POTION -> this.isPotion = true;
            case TIPPED_ARROW -> this.isTippedArrow = true;
            case FILLED_MAP -> this.isShield = true;
            case SHIELD -> this.isShield = true;
        }

        String name = this.material.name();

        this.isArmor = name.endsWith("_HELMET") || name.endsWith("_CHESTPLATE") || name.endsWith("_LEGGINGS") || name.endsWith("_BOOTS");

        this.isBanner = name.endsWith("BANNER");

        return this;
    }

    // Custom Heads
    public void setPlayer(String player) {
        this.player = player;
    }

    // Enchantments
    public ItemBuilder addEnchantments(HashMap<Enchantment, Integer> enchantments, boolean unsafeEnchantments) {
        enchantments.forEach((enchantment, level) -> addEnchantment(enchantment, level, unsafeEnchantments));
        return this;
    }

    public ItemBuilder addEnchantment(Enchantment enchantment, int level, boolean unsafeEnchantments) {
        getItemStack().editMeta(meta -> {
            meta.addEnchant(enchantment, level, unsafeEnchantments);
        });

        return this;
    }

    public ItemBuilder removeEnchantment(Enchantment enchantment) {
        getItemStack().editMeta(meta -> {
            meta.removeEnchant(enchantment);
        });

        return this;
    }

    private boolean isValidInteger(String value) {
        try {
            Integer.parseInt(value);
        } catch (NumberFormatException exception) {
            return false;
        }

        return true;
    }

    private @NotNull OfflinePlayer getOfflinePlayer(String player) {
        CompletableFuture<UUID> future = CompletableFuture.supplyAsync(() -> this.plugin.getServer().getOfflinePlayer(player)).thenApply(OfflinePlayer::getUniqueId);

        return this.plugin.getServer().getOfflinePlayer(future.join());
    }

    private Player getPlayer(String player) {
        return this.plugin.getServer().getPlayer(player);
    }
}