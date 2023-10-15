package com.ryderbelserion.cluster.paper.items;

import com.destroystokyo.paper.profile.PlayerProfile;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.ryderbelserion.cluster.paper.utils.DyeUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.minecraft.nbt.TagParser;
import org.apache.commons.lang.WordUtils;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.bukkit.FireworkEffect;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.OfflinePlayer;
import org.bukkit.Registry;
import org.bukkit.block.Banner;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.block.banner.Pattern;
import org.bukkit.block.banner.PatternType;
import org.bukkit.craftbukkit.v1_20_R1.inventory.CraftItemStack;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
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
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;

public class ItemBuilder {

    private JavaPlugin plugin;

    // Items
    private Material material = Material.STONE;
    private ItemStack itemStack = null;
    private int itemAmount = 1;

    // NBT Data
    private String itemData = "";

    // Display
    private Component displayName = Component.empty();
    private List<Component> displayLore = new ArrayList<>();
    private int itemDamage = this.material.getMaxDurability();

    // Model Data
    private boolean hasCustomModelData = false;
    private int customModelData = 0;

    // Potions
    private boolean isPotion = false;
    private Color potionColor = Color.RED;
    private PotionEffectType potionType = null;
    private int potionDuration = -1;
    private int potionAmplifier = 1;

    // Player Heads
    private String player = "";
    private boolean isHead = false;
    private boolean isURL = false;

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
    private List<Pattern> patterns = new ArrayList<>();

    // Shields
    private boolean isShield = false;

    // Maps
    private boolean isMap = false;
    private Color mapColor = Color.RED;

    // Fireworks
    private boolean isFirework;
    private boolean isFireworkStar;
    private Color fireworkColor;
    private List<Color> fireworkColors;
    private int fireworkPower;

    // Enchantments or ItemFlags
    private boolean isUnbreakable = false;

    private boolean hideItemFlags = false;
    private List<ItemFlag> itemFlags = new ArrayList<>();

    private boolean isGlowing = false;

    private boolean isSpawner = false;
    private EntityType entityType = EntityType.BAT;

    // Create a new item.
    public ItemBuilder(JavaPlugin plugin, ItemStack itemStack) {
        this.plugin = plugin;

        this.itemStack = itemStack;

        this.material = itemStack.getType();

        switch (this.material) {
            case LEATHER_HELMET, LEATHER_CHESTPLATE, LEATHER_LEGGINGS, LEATHER_BOOTS, LEATHER_HORSE_ARMOR -> this.isLeatherArmor = true;
            case POTION, SPLASH_POTION, LINGERING_POTION -> this.isPotion = true;
            case FIREWORK_STAR -> this.isFireworkStar = true;
            case TIPPED_ARROW -> this.isTippedArrow = true;
            case FIREWORK_ROCKET -> this.isFirework = true;
            case FILLED_MAP -> this.isMap = true;
            case PLAYER_HEAD -> this.isHead = true;
            case SPAWNER -> this.isSpawner = true;
            case SHIELD -> this.isShield = true;
        }

        String name = this.material.name();

        this.isArmor = name.endsWith("_HELMET") || name.endsWith("_CHESTPLATE") || name.endsWith("_LEGGINGS") || name.endsWith("_BOOTS");

        this.isBanner = name.endsWith("BANNER");
    }

    // De-duplicate an item builder.
    public ItemBuilder(ItemBuilder itemBuilder) {
        this.material = itemBuilder.material;
        this.itemStack = itemBuilder.itemStack;
        this.itemAmount = itemBuilder.itemAmount;
        this.itemData = itemBuilder.itemData;

        this.displayName = itemBuilder.displayName;
        this.displayLore = itemBuilder.displayLore;
        this.itemDamage = itemBuilder.itemDamage;

        this.hasCustomModelData = itemBuilder.hasCustomModelData;
        this.customModelData = itemBuilder.customModelData;

        this.isPotion = itemBuilder.isPotion;
        this.potionColor = itemBuilder.potionColor;
        this.potionType = itemBuilder.potionType;
        this.potionDuration = itemBuilder.potionDuration;
        this.potionAmplifier = itemBuilder.potionAmplifier;

        this.player = itemBuilder.player;
        this.isHead = itemBuilder.isHead;
        this.isURL = itemBuilder.isURL;

        this.isTippedArrow = itemBuilder.isTippedArrow;

        this.isLeatherArmor = itemBuilder.isLeatherArmor;
        this.isArmor = itemBuilder.isArmor;

        this.trimMaterial = itemBuilder.trimMaterial;
        this.trimPattern = itemBuilder.trimPattern;
        this.armorColor = itemBuilder.armorColor;

        this.isBanner = itemBuilder.isBanner;
        this.patterns = itemBuilder.patterns;

        this.isShield = itemBuilder.isShield;

        this.isMap = itemBuilder.isMap;
        this.mapColor = itemBuilder.mapColor;

        this.isFirework = itemBuilder.isFirework;
        this.isFireworkStar = itemBuilder.isFireworkStar;
        this.fireworkColor = itemBuilder.fireworkColor;
        this.fireworkColors = itemBuilder.fireworkColors;
        this.fireworkPower = itemBuilder.fireworkPower;

        this.isUnbreakable = itemBuilder.isUnbreakable;

        this.hideItemFlags = itemBuilder.hideItemFlags;
        this.itemFlags = itemBuilder.itemFlags;

        this.isGlowing = itemBuilder.isGlowing;

        this.isSpawner = itemBuilder.isSpawner;
        this.entityType = itemBuilder.entityType;
    }

    public ItemBuilder(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    private Component parse(String message) {
        return MiniMessage.miniMessage().deserialize(message).decorationIfAbsent(TextDecoration.ITALIC, TextDecoration.State.FALSE);
    }

    public ItemStack build() {
        if (this.itemStack == null) {
            this.itemStack = new ItemStack(Material.STONE);

            this.itemStack.editMeta(meta -> meta.displayName(parse("<red>An error has occurred with the item builder.")));

            return this.itemStack;
        }

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

            getItemStack().setAmount(this.itemAmount);

            getItemStack().editMeta(meta -> {
                if (this.isHead && !this.player.isBlank()) {
                    SkullMeta skullMeta = (SkullMeta) meta;

                    if (this.isURL) {
                        //TODO() This doesn't work.
                        PlayerProfile profile = Bukkit.getServer().createProfile(UUID.randomUUID());

                        PlayerTextures textures = profile.getTextures();

                        try {
                            textures.setSkin(new URL(this.player));
                        } catch (MalformedURLException exception) {
                            this.plugin.getLogger().log(Level.WARNING, "Failed to set skin: " + this.player + " to profile.", exception);
                        }
                    } else {
                        OfflinePlayer person = getPlayer(this.player) != null ? getPlayer(this.player) : getOfflinePlayer(this.player);

                        skullMeta.setOwningPlayer(person);
                    }
                }

                // Set the display name.
                if (!this.displayName.equals(Component.empty())) {
                    meta.displayName(this.displayName);
                }

                // Set the lore.
                if (!this.displayLore.isEmpty()) {
                    meta.lore(this.displayLore);
                }

                if (this.isSpawner) {
                    if (this.displayName.equals(Component.empty())) {
                        meta.displayName(parse(WordUtils.capitalizeFully(this.entityType.getKey().getKey().replaceAll("_", " ")) + " Spawner"));
                    }

                    BlockStateMeta blockState = (BlockStateMeta) meta;

                    CreatureSpawner creatureSpawner = (CreatureSpawner) blockState.getBlockState();

                    creatureSpawner.setSpawnedType(this.entityType);
                    blockState.setBlockState(creatureSpawner);
                }

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
                        PotionEffect effect = new PotionEffect(this.potionType, this.potionDuration, this.potionAmplifier);

                        potionMeta.addCustomEffect(effect, true);

                        potionMeta.setBasePotionData(new PotionData(PotionType.valueOf(effect.getType().getName())));
                    }

                    if (this.potionColor != null) {
                        potionMeta.setColor(this.potionColor);
                    }
                }

                if (this.isLeatherArmor && this.armorColor != null) {
                    LeatherArmorMeta leatherArmorMeta = (LeatherArmorMeta) meta;

                    leatherArmorMeta.setColor(this.armorColor);
                }

                if (this.isBanner && !this.patterns.isEmpty()) {
                    BannerMeta bannerMeta = (BannerMeta) meta;
                    bannerMeta.setPatterns(this.patterns);
                }

                if (this.isShield && !this.patterns.isEmpty()) {
                    BlockStateMeta shieldMeta = (BlockStateMeta) meta;

                    Banner banner = (Banner) shieldMeta.getBlockState();
                    banner.setPatterns(this.patterns);
                    banner.update();

                    shieldMeta.setBlockState(banner);
                }

                // If the item has model data.
                if (this.hasCustomModelData) {
                    meta.setCustomModelData(this.customModelData);
                }

                this.itemFlags.forEach(meta::addItemFlags);

                if (this.hideItemFlags) {
                    meta.addItemFlags(ItemFlag.values());
                }

                meta.setUnbreakable(this.isUnbreakable);
            });
        } else {
            this.plugin.getLogger().warning("Material cannot be air or null.");
        }

        addGlow();

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
        if (displayName.isEmpty() || displayName.isBlank()) {
            this.displayName = parse(this.material.name());
            return this;
        }

        this.displayName = parse(displayName);

        return this;
    }

    public ItemBuilder setDisplayLore(List<String> displayLore) {
        if (displayLore != null) {
            if (!this.displayLore.isEmpty()) this.displayLore.clear();

            displayLore.forEach(this::addDisplayLore);
        }

        return this;
    }

    public ItemBuilder addDisplayLore(String lore) {
        this.displayLore.add(parse(lore));
        return this;
    }

    public ItemBuilder addPatterns(List<String> patterns) {
        patterns.forEach(this::addPatterns);
        return this;
    }

    public ItemBuilder addPattern(Pattern pattern) {
        this.patterns.add(pattern);
        return this;
    }

    public ItemBuilder setPattern(List<Pattern> patterns) {
        this.patterns = patterns;
        return this;
    }

    public ItemBuilder setTrimMaterial(TrimMaterial trimMaterial) {
        this.trimMaterial = trimMaterial;
        return this;
    }

    public ItemBuilder setTrimPattern(TrimPattern trimPattern) {
        this.trimPattern = trimPattern;
        return this;
    }

    public ItemBuilder setItemDamage(int itemDamage) {
        this.itemDamage = itemDamage;
        return this;
    }

    public ItemBuilder setAmount(Integer amount) {
        this.itemAmount = amount;
        return this;
    }

    public ItemBuilder setPotionAmplifier(int potionAmplifier) {
        this.potionAmplifier = potionAmplifier;
        return this;
    }

    public ItemBuilder setPotionDuration(int potionDuration) {
        this.potionDuration = potionDuration;
        return this;
    }

    public ItemBuilder setGlowing(boolean isGlowing) {
        this.isGlowing = isGlowing;
        return this;
    }

    public boolean isUnbreakable() {
        return this.isUnbreakable;
    }

    public void setUnbreakable(boolean unbreakable) {
        this.isUnbreakable = unbreakable;
    }

    public ItemBuilder addItemFlag(ItemFlag itemFlag) {
        if (itemFlag != null) this.itemFlags.add(itemFlag);
        return this;
    }

    public ItemBuilder addFlag(String flag) {
        ItemFlag value = getFlag(flag);

        if (value != null) this.itemFlags.add(value);
        return this;
    }

    public ItemBuilder addItemFlags(List<String> flags) {
        for (String flag : flags) {
            try {
                ItemFlag itemFlag = ItemFlag.valueOf(flag.toUpperCase());

                addItemFlag(itemFlag);
            } catch (Exception ignored) {}
        }

        return this;
    }

    public ItemBuilder setEffect(FireworkEffect.Builder... effects) {
        return setEffect(Arrays.asList(effects));
    }

    public ItemBuilder setEffect(List<FireworkEffect.Builder> effects) {
        if (effects.isEmpty()) return this;

        getItemStack().editMeta(meta -> {
            FireworkMeta fireworkMeta = (FireworkMeta) meta;

            if (this.isFirework) {
                effects.forEach(eff -> fireworkMeta.addEffects(eff.build()));
            }

            if (this.isFireworkStar) {
                fireworkMeta.addEffects(effects.get(0).build());
            }
        });

        return this;
    }

    public ItemBuilder setFireworkPower(int power) {
        if (this.isFirework) {
            getItemStack().editMeta(meta -> {
                FireworkMeta fireworkMeta = (FireworkMeta) meta;
                fireworkMeta.setPower(power);
            });
        }

        return this;
    }

    public ItemBuilder setItemData(String itemData) {
        this.itemData = itemData;

        return this;
    }

    public ItemBuilder setEntityType(EntityType entityType) {
        this.entityType = entityType;
        return this;
    }

    // Set the material type.
    public ItemBuilder setMaterial(Material material) {
        this.material = material;

        if (this.itemStack != null) this.itemStack.setType(this.material); else this.itemStack = new ItemStack(this.material);

        this.isHead = material == Material.PLAYER_HEAD;
        return this;
    }

    // Custom Heads
    public ItemBuilder setPlayer(String player) {
        this.player = player;

        if (this.player != null && this.player.length() > 16) {
            this.isURL = this.player.startsWith("http");
        }

        return this;
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

    public ItemBuilder setMaterial(String material) {
        if (material == null || material.isEmpty()) {
            List.of(
                    "Material cannot be null or empty, Output: " + material + ".",
                    "Please take a screenshot of this before asking for support."
            ).forEach(line -> {
                this.plugin.getLogger().warning(line);
            });

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

            metaData = metaData.replace("#" + this.customModelData, "");

            if (isValidInteger(metaData)) {
                this.itemDamage = Integer.parseInt(metaData);
            } else {
                try {
                    this.potionType = Registry.POTION_EFFECT_TYPE.get(NamespacedKey.minecraft(metaData));
                } catch (Exception exception) {
                    this.plugin.getLogger().log(Level.WARNING, "Failed to set potion type " + metaData + ".", exception);
                }

                this.potionColor = DyeUtils.getColor(metaData);
                this.armorColor = DyeUtils.getColor(metaData);
                this.mapColor = DyeUtils.getColor(metaData);
                this.fireworkColor = DyeUtils.getColor(metaData);
            }
        } else if (material.contains("#")) {
            String[] type = material.split("#");
            material = type[0];

            if (isValidInteger(type[1])) {
                this.hasCustomModelData = true;
                this.customModelData = Integer.parseInt(type[1]);
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
            case FIREWORK_STAR -> this.isFireworkStar = true;
            case TIPPED_ARROW -> this.isTippedArrow = true;
            case FIREWORK_ROCKET -> this.isFirework = true;
            case FILLED_MAP -> this.isMap = true;
            case PLAYER_HEAD -> this.isHead = true;
            case SPAWNER -> this.isSpawner = true;
            case SHIELD -> this.isShield = true;
        }

        String name = this.material.name();

        this.isArmor = name.endsWith("_HELMET") || name.endsWith("_CHESTPLATE") || name.endsWith("_LEGGINGS") || name.endsWith("_BOOTS");

        this.isBanner = name.endsWith("BANNER");

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
        CompletableFuture<UUID> future = CompletableFuture.supplyAsync(() -> Bukkit.getServer().getOfflinePlayer(player)).thenApply(OfflinePlayer::getUniqueId);

        return Bukkit.getServer().getOfflinePlayer(future.join());
    }

    private Player getPlayer(String player) {
        return Bukkit.getServer().getPlayer(player);
    }

    private void addGlow() {
        if (this.isGlowing) {
            if (getItemStack().getItemMeta().hasEnchants()) return;

            getItemStack().addEnchantment(Enchantment.LUCK, 1);

            getItemStack().editMeta(meta -> meta.addItemFlags(ItemFlag.HIDE_ENCHANTS));
        }
    }

    private void addPatterns(String stringPattern) {
        try {
            String[] split = stringPattern.split(":");

            for (PatternType pattern : PatternType.values()) {
                if (split[0].equalsIgnoreCase(pattern.name()) || split[0].equalsIgnoreCase(pattern.getIdentifier())) {
                    DyeColor color = DyeUtils.getDyeColor(split[1]);

                    if (color != null) {
                        addPattern(new Pattern(color, pattern));
                    }

                    break;
                }
            }
        } catch (Exception ignored) {}
    }

    private ItemFlag getFlag(String flagString) {
        for (ItemFlag flag : ItemFlag.values()) {
            if (flag.name().equalsIgnoreCase(flagString)) return flag;
        }

        return null;
    }
}