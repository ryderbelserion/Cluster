package com.ryderbelserion.cluster.bukkit.items;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.ryderbelserion.cluster.bukkit.BukkitPlugin;
import com.ryderbelserion.cluster.bukkit.api.adventure.FancyLogger;
import com.ryderbelserion.cluster.bukkit.api.registry.BukkitProvider;
import com.ryderbelserion.cluster.bukkit.items.utils.DyeUtils;
import net.minecraft.nbt.TagParser;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.craftbukkit.v1_20_R1.inventory.CraftItemStack;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;
import org.jetbrains.annotations.NotNull;
import java.util.HashMap;
import java.util.List;

@SuppressWarnings("ALL")
public class BaseItemBuilder<Base extends BaseItemBuilder<Base>> {

    private final @NotNull BukkitPlugin provider = BukkitProvider.get();

    private final @NotNull JavaPlugin plugin = this.provider.getPlugin();

    private final Material material;

    private ItemStack itemStack;
    private ItemMeta itemMeta;

    private String itemData;

    private String displayName;
    private List<String> displayLore;

    // Model Data
    private boolean hasCustomModelData;

    private int customModelData;

    // Potions
    private boolean isPotion;
    private Color potionColor;
    private PotionEffectType potionType;

    private boolean isTippedArrow;

    protected BaseItemBuilder(ItemStack itemStack) {
        this.itemStack = itemStack;

        this.material = itemStack.getType();

        this.itemData = "";

        this.potionType = null;
        this.potionColor = null;
        this.isPotion = false;

        this.isTippedArrow = false;

        this.hasCustomModelData = false;

        switch (this.material) {
            case POTION, SPLASH_POTION, LINGERING_POTION -> this.isPotion = true;
        }

        this.itemMeta = itemStack.hasItemMeta() ? itemStack.getItemMeta() : Bukkit.getServer().getItemFactory().getItemMeta(this.material);
    }

    public ItemStack build() {
        if (!isAir()) {
            // If item data is not empty. We ignore all other options and simply return.
            if (!this.itemData.isEmpty()) {
                net.minecraft.world.item.ItemStack nmsItem = CraftItemStack.asNMSCopy(this.itemStack);

                try {
                    nmsItem.setTag(TagParser.parseTag(this.itemData));
                } catch (CommandSyntaxException e) {
                    e.printStackTrace();
                }

                return CraftItemStack.asBukkitCopy(nmsItem);
            }

            // Check if is potion and return if it is because we don't need anything else after.
            if (this.isPotion) {
                PotionMeta potionMeta = (PotionMeta) this.itemMeta;

                if (this.potionType != null) {
                    potionMeta.set
                    potionMeta.setBasePotionData(new PotionData(this.potionType.));
                }

                if (this.potionColor != null) {
                    potionMeta.setColor(this.potionColor);
                }

                if (this.hasCustomModelData) {
                    potionMeta.setCustomModelData(this.customModelData);
                }

                this.itemMeta = potionMeta;

                this.itemStack.setItemMeta(this.itemMeta);

                return this.itemStack;
            }
        } else {
            FancyLogger.warn("Material cannot be air.");
        }

        getItemStack().setItemMeta(getItemMeta());

        return getItemStack();
    }

    private boolean isAir() {
        return this.material.isAir();
    }

    private ItemMeta getItemMeta() {
        return this.itemMeta;
    }

    private ItemStack getItemStack() {
        return this.itemStack;
    }

    public void setItemData(String itemData) {
        this.itemData = itemData;
    }

    // Set the material type.
    public Base setValue(String material) {
        if (material == null || material.isEmpty()) {
            List.of(
                    "Material cannot be null or empty, Output: " + material + ".",
                    "Please take a screenshot of this before asking for support."
            ).forEach(FancyLogger::warn);

            return (Base) this;
        }

        if (this.isPotion) {
            if (material.contains(":")) {
                String[] section = material.split(":");

                material = section[0];
                String metaData = section[1];

                FancyLogger.debug("Mat: " + material);

                FancyLogger.debug("Meta: " + metaData);

                if (metaData.contains("#")) {
                    String modelData = metaData.split("#")[1];

                    if (isValidInteger(modelData)) {
                        this.hasCustomModelData = true;
                        this.customModelData = Integer.parseInt(modelData);
                    }
                }

                try {
                    NamespacedKey key = new NamespacedKey(this.plugin, metaData.replace("#" + this.customModelData, ""));

                    this.potionType = PotionEffectType.getByKey(key);
                } catch (Exception exception) {
                    FancyLogger.warn("Failed to set potion type " + metaData + ".");
                    FancyLogger.debug(exception.getMessage());
                }

                this.potionColor = DyeUtils.getColor(metaData);
            }

            return (Base) this;
        }

        return (Base) this;
    }

    // Enchantments
    public Base addEnchantments(HashMap<Enchantment, Integer> enchantments, boolean unsafeEnchantments) {
        enchantments.forEach((enchantment, level) -> addEnchantment(enchantment, level, unsafeEnchantments));
        return (Base) this;
    }

    public Base addEnchantment(Enchantment enchantment, int level, boolean unsafeEnchantments) {
        getItemMeta().addEnchant(enchantment, level, unsafeEnchantments);
        return (Base) this;
    }

    public Base removeEnchantment(Enchantment enchantment) {
        getItemMeta().removeEnchant(enchantment);
        return (Base) this;
    }

    private boolean isValidInteger(String value) {
        try {
            Integer.parseInt(value);
        } catch (NumberFormatException exception) {
            return false;
        }

        return true;
    }
}