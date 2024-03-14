package com.ryderbelserion.cluster.items;

import com.google.common.collect.Multimap;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.ryderbelserion.cluster.Cluster;
import com.ryderbelserion.cluster.ClusterProvider;
import com.ryderbelserion.cluster.api.enums.PluginSupport;
import com.ryderbelserion.cluster.utils.AdvUtils;
import com.ryderbelserion.cluster.utils.DyeUtils;
import com.ryderbelserion.cluster.utils.ItemUtils;
import io.th0rgal.oraxen.api.OraxenItems;
import me.arcaniax.hdb.api.HeadDatabaseAPI;
import me.clip.placeholderapi.PlaceholderAPI;
import net.kyori.adventure.text.Component;
import net.minecraft.nbt.TagParser;
import org.apache.commons.lang.WordUtils;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.bukkit.FireworkEffect;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.block.Banner;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.block.banner.Pattern;
import org.bukkit.block.banner.PatternType;
import org.bukkit.craftbukkit.v1_20_R3.inventory.CraftItemStack;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.*;
import org.bukkit.inventory.meta.trim.ArmorTrim;
import org.bukkit.inventory.meta.trim.TrimMaterial;
import org.bukkit.inventory.meta.trim.TrimPattern;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;
import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class ItemBuilder {

    @NotNull
    private final Cluster provider = ClusterProvider.get();

    private final boolean isLogging = this.provider.isLogging();

    @NotNull
    private final Logger logger = this.provider.getLogger();

    // Items
    private Material material = Material.STONE;
    private ItemStack itemStack = null;
    private int itemAmount = 1;

    // NBT Data
    private String itemData = "";

    // Display
    private Component displayName = Component.empty();
    private List<Component> displayLore = new ArrayList<>();
    private int itemDamage = 0;

    // Model Data
    private boolean hasCustomModelData = false;
    private int customModelData = 0;
    private String customMaterial = "";

    // Potions
    private boolean isPotion = false;
    private Color potionColor = Color.RED;
    private PotionEffectType potionType = null;
    private int potionDuration = -1;
    private int potionAmplifier = 1;

    // Player Heads
    private UUID uuid = null;
    private boolean isLink = false;
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
    private List<Pattern> patterns = new ArrayList<>();

    // Shields
    private boolean isShield = false;

    // Maps
    private boolean isMap = false;
    private Color mapColor = Color.RED;

    // Fireworks
    private boolean isFirework = false;
    private boolean isFireworkStar = false;
    private Color fireworkColor = Color.RED;
    private List<Color> fireworkColors = new ArrayList<>();
    private int fireworkPower = 1;

    // Enchantments or ItemFlags
    private boolean isUnbreakable = false;

    private boolean hideItemFlags = false;
    private List<ItemFlag> itemFlags = new ArrayList<>();

    private boolean isGlowing = false;

    private boolean isSpawner = false;
    private EntityType entityType = EntityType.BAT;

    // Attributes
    private boolean isTool = false;

    // Placeholders
    private Map<String, String> namePlaceholders = new HashMap<>();
    private Map<String, String> lorePlaceholders = new HashMap<>();

    // Create a new item.
    public ItemBuilder(ItemStack itemStack) {
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

        this.isTool = name.endsWith("_SWORD") || name.endsWith("_AXE") || name.endsWith("_SHOVEL");

        this.isBanner = name.endsWith("BANNER");
    }

    private Player target = null;

    public ItemBuilder(Player target, ItemStack itemStack) {
        this.target = target;

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

        this.isTool = name.endsWith("_SWORD") || name.endsWith("_AXE") || name.endsWith("_SHOVEL");

        this.isBanner = name.endsWith("BANNER");
    }

    // De-duplicate an item builder.
    public ItemBuilder(ItemBuilder itemBuilder) {
        this.target = itemBuilder.target;

        this.material = itemBuilder.material;
        this.itemStack = itemBuilder.itemStack;

        this.customMaterial = itemBuilder.customMaterial;

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

        this.uuid = itemBuilder.uuid;
        this.isHead = itemBuilder.isHead;

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

        this.isTool = itemBuilder.isTool;

        this.namePlaceholders = new HashMap<>(itemBuilder.namePlaceholders);
        this.lorePlaceholders = new HashMap<>(itemBuilder.lorePlaceholders);
    }

    public ItemBuilder() {}

    private Component parse(String message) {
        if (this.provider.isPapiEnabled() && this.target != null) {
            return AdvUtils.parse(PlaceholderAPI.setPlaceholders(this.target, message));
        }

        return AdvUtils.parse(message);
    }

    public ItemStack build() {
        // Check if oraxen is enabled.
        if (this.provider.isOraxenEnabled()) {
            // Get the item.
            io.th0rgal.oraxen.items.ItemBuilder oraxenItem = OraxenItems.getItemById(this.customMaterial);

            if (oraxenItem != null) {
                // If the item isn't null, we don't need to re-build.
                if (this.itemStack != null) {
                    this.material = this.itemStack.getType();

                    return this.itemStack;
                }

                // This is just here in case it is null for whatever reason.
                this.itemStack = oraxenItem.build();

                this.material = this.itemStack.getType();

                return this.itemStack;
            }
        }

        if (!isAir()) {
            // If item data is not empty. We ignore all other options and simply return.
            if (!this.itemData.isEmpty()) {
                net.minecraft.world.item.ItemStack nmsItem = CraftItemStack.asNMSCopy(getItemStack());

                try {
                    nmsItem.setTag(TagParser.parseTag(this.itemData));
                } catch (CommandSyntaxException exception) {
                    this.logger.log(Level.WARNING, "Failed to set nms tag.", exception);
                }

                return CraftItemStack.asBukkitCopy(nmsItem);
            }

            getItemStack().setAmount(this.itemAmount);

            getItemStack().editMeta(itemMeta -> {
                if (this.isHead && this.uuid != null) {
                    SkullMeta skullMeta = (SkullMeta) itemMeta;

                    OfflinePlayer person = getPlayer(this.uuid) != null ? getPlayer(this.uuid) : getOfflinePlayer(this.uuid);

                    skullMeta.setOwningPlayer(person);
                }

                // Set the display name.
                if (!this.displayName.equals(Component.empty())) {
                    itemMeta.displayName(this.displayName);
                }

                // Set the lore.
                if (!this.displayLore.isEmpty()) {
                    itemMeta.lore(this.displayLore);
                }

                if (this.isSpawner) {
                    if (this.displayName.equals(Component.empty())) {
                        itemMeta.displayName(parse(WordUtils.capitalizeFully(this.entityType.getKey().getKey().replaceAll("_", " ")) + " Spawner"));
                    }

                    BlockStateMeta blockState = (BlockStateMeta) itemMeta;

                    CreatureSpawner creatureSpawner = (CreatureSpawner) blockState.getBlockState();

                    creatureSpawner.setSpawnedType(this.entityType);

                    blockState.setBlockState(creatureSpawner);
                }

                // If the item is able to be damaged.
                if (itemMeta instanceof Damageable damageable) {
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
                        ArmorMeta armorMeta = (ArmorMeta) itemMeta;

                        armorMeta.setTrim(new ArmorTrim(this.trimMaterial, this.trimPattern));
                    }
                }

                if (this.isMap) {
                    MapMeta mapMeta = (MapMeta) itemMeta;

                    mapMeta.setScaling(true);

                    if (this.mapColor != null) mapMeta.setColor(this.mapColor);
                }

                // Check if is potion and apply potion related settings.
                if (this.isPotion || this.isTippedArrow) {
                    PotionMeta potionMeta = (PotionMeta) itemMeta;

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
                    LeatherArmorMeta leatherArmorMeta = (LeatherArmorMeta) itemMeta;

                    leatherArmorMeta.setColor(this.armorColor);
                }

                if (this.isBanner && !this.patterns.isEmpty()) {
                    BannerMeta bannerMeta = (BannerMeta) itemMeta;
                    bannerMeta.setPatterns(this.patterns);
                }

                if (this.isShield && !this.patterns.isEmpty()) {
                    BlockStateMeta shieldMeta = (BlockStateMeta) itemMeta;

                    Banner banner = (Banner) shieldMeta.getBlockState();
                    banner.setPatterns(this.patterns);
                    banner.update();

                    shieldMeta.setBlockState(banner);
                }

                // If the item has model data.
                if (this.hasCustomModelData) {
                    itemMeta.setCustomModelData(this.customModelData);
                }

                this.itemFlags.forEach(itemMeta::addItemFlags);

                if (this.hideItemFlags) {
                    itemMeta.addItemFlags(ItemFlag.values());
                }

                itemMeta.setUnbreakable(this.isUnbreakable);

                if (this.isGlowing) {
                    if (!itemMeta.hasEnchants()) {
                        itemMeta.addEnchant(Enchantment.LUCK, 1, false);
                        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                    }
                }
            });
        } else {
            if (this.isLogging) this.logger.warning("Material cannot be air or null.");
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
        if (displayName.isEmpty()) {
            this.displayName = parse(this.material.name());
            return this;
        }

        this.displayName = parse(displayName);

        return this;
    }

    public ItemBuilder setDisplayLore(List<String> displayLore) {
        if (displayLore != null && !displayLore.isEmpty()) {
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

    public ItemBuilder setTrimMaterial(String trimMaterial) {
        this.trimMaterial = ItemUtils.getTrimMaterial(trimMaterial);

        return this;
    }

    public ItemBuilder setTrimPattern(String trimPattern) {
        this.trimPattern = ItemUtils.getTrimPattern(trimPattern);

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

    public ItemBuilder setUnbreakable(boolean unbreakable) {
        this.isUnbreakable = unbreakable;

        return this;
    }

    public ItemBuilder addItemFlag(ItemFlag itemFlag) {
        if (itemFlag != null) this.itemFlags.add(itemFlag);

        return this;
    }

    public ItemBuilder hideItemFlags(boolean hideItemFlags) {
        this.hideItemFlags = hideItemFlags;

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

        getItemStack().editMeta(itemMeta -> {
            FireworkMeta fireworkMeta = (FireworkMeta) itemMeta;

            if (this.isFirework) {
                effects.forEach(eff -> fireworkMeta.addEffects(eff.build()));
            }

            if (this.isFireworkStar) {
                fireworkMeta.addEffects(effects.get(0).build());
            }
        });

        return this;
    }

    /**
     * Set firework power if the item is a firework.
     *
     * @param power the power which is how high it flies.
     * @return the ItemBuilder with updated data.
     */
    public ItemBuilder setFireworkPower(int power) {
        if (this.isFirework) {
            getItemStack().editMeta(itemMeta -> {
                FireworkMeta fireworkMeta = (FireworkMeta) itemMeta;
                fireworkMeta.setPower(power);
            });
        }

        return this;
    }

    /**
     * Set item data which is used for NMS.
     *
     * @param itemData the item data to set.
     * @return the ItemBuilder with updated data.
     */
    public ItemBuilder setItemData(String itemData) {
        this.itemData = itemData;

        return this;
    }

    /**
     * Set entity type
     *
     * @param entityType the type to set.
     * @return the ItemBuilder with updated data.
     */
    public ItemBuilder setEntityType(EntityType entityType) {
        this.entityType = entityType;

        return this;
    }

    /**
     * Sets the material.
     *
     * @param material the material to use.
     * @return the ItemBuilder with updated data.
     */
    public ItemBuilder setMaterial(Material material) {
        this.material = material;

        if (this.itemStack != null) this.itemStack.setType(this.material); else this.itemStack = new ItemStack(this.material);

        this.isHead = material == Material.PLAYER_HEAD;

        return this;
    }

    /**
     * Sets the player uuid.
     *
     * @param uuid the uuid to set.
     * @return the ItemBuilder with updated data.
     */
    public ItemBuilder setUUID(UUID uuid) {
        this.uuid = uuid;

        return this;
    }

    /**
     * Set a player skull if HeadDatabase is enabled.
     *
     * @param skull the skull to use.
     * @return the ItemBuilder with updated data.
     */
    public ItemBuilder setSkull(String skull, HeadDatabaseAPI api) {
        if (api == null) {
            if (this.isLogging) this.logger.warning("HeadDatabaseAPI is not loaded.");

            return this;
        }

        if (api.isHead(skull)) {
            this.itemStack = api.getItemHead(skull);
        }

        return this;
    }

    /**
     * Adds attributes to items.
     *
     * @param attribute the attribute to add.
     * @return the ItemBuilder with updated data.
     */
    public ItemBuilder addAttribute(String attribute) {
        if (!isTool()) {
            if (this.isLogging) this.logger.warning("The item is not a tool, Cannot modify attributes.");

            return this;
        }

        if (attribute == null || attribute.isEmpty() || attribute.isBlank()) {
            if (this.isLogging) this.logger.warning("Attributes cannot be empty/blank/null");

            return this;
        }

        String[] section = attribute.split(",");
        Attribute modifier = ItemUtils.getAttribute(section[0]);

        if (modifier == null || !isValidDouble(section[1])) return this;

        double damage = Double.parseDouble(section[1]);

        AttributeModifier.Operation operation = AttributeModifier.Operation.valueOf(section[2]);
        EquipmentSlot equipmentSlot = EquipmentSlot.valueOf(section[3]);

        getItemStack().editMeta(itemMeta -> {
            AttributeModifier attributeModifier = new AttributeModifier(UUID.randomUUID(), modifier.getKey().value(), damage, operation, equipmentSlot);

            itemMeta.addAttributeModifier(modifier, attributeModifier);
        });

        return this;
    }

    /**
     * Set a hashmap of modifiers.
     *
     * @param modifiers the modifiers to set.
     * @return the ItemBuilder with updated data.
     */
    public ItemBuilder addAttribute(Multimap<Attribute, AttributeModifier> modifiers) {
        if (modifiers == null || modifiers.isEmpty()) {
            if (this.isLogging) this.logger.warning("The multi map for attribute/attributemodifier is empty.");

            return this;
        }

        getItemStack().editMeta(itemMeta -> itemMeta.setAttributeModifiers(modifiers));

        return this;
    }

    /**
     * @return true if a tool otherwise false.
     */
    public boolean isTool() {
        return this.isTool;
    }

    /**
     * Adds multiple enchantments to an item.
     *
     * @param enchantments the map of enchantments, String/Integer
     * @param unsafeEnchantments if the enchantments are higher than the vanilla defaults.
     * @return the ItemBuilder with updated data.
     */
    public ItemBuilder addEnchantments(Map<String, Integer> enchantments, boolean unsafeEnchantments) {
        enchantments.forEach((enchantment, level) -> addEnchantment(enchantment, level, unsafeEnchantments));

        return this;
    }

    /**
     * Adds a single enchantment to an item.
     *
     * @param type the type of enchantment.
     * @param level the level of the enchantment.
     * @param unsafeEnchantments if the enchantment is higher than the vanilla defaults.
     * @return the ItemBuilder with updated data.
     */
    public ItemBuilder addEnchantment(String type, int level, boolean unsafeEnchantments) {
        Enchantment enchantment = ItemUtils.getEnchantment(type);

        if (enchantment == null || !isValidInteger(String.valueOf(level))) return this;

        getItemStack().editMeta(itemMeta -> {
            if (itemMeta.hasConflictingEnchant(enchantment)) {
                if (this.isLogging) this.logger.warning("One of the enchants on the item may conflict with " + type + ", The item been enchanted anyway.");
            }

            itemMeta.addEnchant(enchantment, level, unsafeEnchantments);
        });

        return this;
    }

    /**
     * Removes an enchantment.
     *
     * @param type the enchantment to remove.
     * @return the ItemBuilder with updated data.
     */
    public ItemBuilder removeEnchantment(String type) {
        Enchantment enchantment = ItemUtils.getEnchantment(type);

        if (enchantment == null) return this;

        getItemStack().editMeta(itemMeta -> itemMeta.removeEnchant(enchantment));

        return this;
    }

    /**
     * Sets a material based on a String which includes model data and other information.
     *
     * @param type the string to break down to what we need.
     * @return the ItemBuilder with updated data.
     */
    public ItemBuilder setMaterial(String type) {
        if (type == null || type.isEmpty()) {
            List.of(
                    "Material cannot be null or empty, Output: " + type + ".",
                    "Please take a screenshot of this before asking for support."
            ).forEach(line -> {
                if (this.isLogging) this.logger.warning(line);
            });

            return this;
        }

        String metaData;

        this.customMaterial = type;

        if (type.contains(":")) {
            String[] section = type.split(":");

            type = section[0];
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
                this.potionType = ItemUtils.getPotionEffect(metaData);

                this.potionColor = DyeUtils.getColor(metaData);
                this.armorColor = DyeUtils.getColor(metaData);
                this.mapColor = DyeUtils.getColor(metaData);
                this.fireworkColor = DyeUtils.getColor(metaData);
            }
        } else if (type.contains("#")) {
            String[] section = type.split("#");
            type = section[0];

            String modelData = section[1];

            if (isValidInteger(modelData)) {
                this.hasCustomModelData = true;
                this.customModelData = Integer.parseInt(modelData);
            }
        }

        Material material = ItemUtils.getMaterial(type);

        if (material != null) {
            this.itemStack = new ItemStack(material);

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

        this.isTool = name.endsWith("_SWORD") || name.endsWith("_AXE") || name.endsWith("_SHOVEL");

        this.isBanner = name.endsWith("BANNER");

        return this;
    }

    /**
     * Check if a string is a valid integer.
     *
     * @param value the string to check.
     * @return true or false.
     */
    private boolean isValidInteger(String value) {
        try {
            Integer.parseInt(value);
        } catch (NumberFormatException exception) {
            if (this.isLogging) this.logger.warning(value + " is not a valid number.");

            return false;
        }

        return true;
    }

    /**
     * Check if a string is a valid double.
     *
     * @param value the string to check.
     * @return true or false.
     */
    private boolean isValidDouble(String value) {
        try {
            Double.parseDouble(value);
        } catch (NumberFormatException exception) {
            if (this.isLogging) this.logger.warning(value + " is not a valid dpuble.");

            return false;
        }

        return true;
    }

    /**
     * Get an offline player.
     *
     * @param uuid the uuid of the offline player.
     * @return the offline player.
     */
    private @NotNull OfflinePlayer getOfflinePlayer(UUID uuid) {
        return Bukkit.getServer().getOfflinePlayer(uuid);
    }

    /**
     * Get an online player by UUID.
     *
     * @param uuid the uuid of the player.
     * @return a player object.
     */
    private Player getPlayer(UUID uuid) {
        return Bukkit.getServer().getPlayer(uuid);
    }

    /**
     * Adds patterns to items from the registry.
     *
     * @param pattern the string to break down to make the pattern.
     */
    private void addPatterns(String pattern) {
        String[] section = pattern.split(":");

        PatternType type = ItemUtils.getPatternType(section[0]);
        DyeColor color = DyeUtils.getDyeColor(section[1]);

        if (type == null || color == null) return;

        addPattern(new Pattern(color, type));
    }

    /**
     * Gets an item flag.
     *
     * @param flag the flag to check.
     * @return the itemflag.
     */
    private ItemFlag getFlag(String flag) {
        return ItemFlag.valueOf(flag);
    }
}