package com.ryderbelserion.ruby.plugin.items;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.ryderbelserion.ruby.PaperImpl;
import com.ryderbelserion.ruby.plugin.registry.PaperProvider;
import com.ryderbelserion.ruby.utils.ItemUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
import org.bukkit.*;
import org.bukkit.block.Banner;
import org.bukkit.block.banner.Pattern;
import org.bukkit.block.banner.PatternType;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.*;
import org.bukkit.inventory.meta.trim.ArmorTrim;
import org.bukkit.inventory.meta.trim.TrimMaterial;
import org.bukkit.inventory.meta.trim.TrimPattern;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;
import org.bukkit.util.Consumer;
import org.jetbrains.annotations.NotNull;
import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

@SuppressWarnings("unchecked")
public class BaseItemBuilder<Base extends BaseItemBuilder<Base>> {

    private final @NotNull PaperImpl paper = PaperProvider.get();

    private final ItemUtils itemUtils = this.paper.itemUtils();

    private final GsonComponentSerializer gson = GsonComponentSerializer.gson();

    // Core.
    private ItemStack itemStack;
    private ItemMeta itemMeta;

    private Material material;

    private int damage;

    // Custom Lore.
    private Field lore_field;

    // Custom Model Data.
    private boolean hasCustomModelData;

    private int customModelData;

    // Custom Heads.
    private boolean isHead;
    private Field profile;
    private String texture;

    // Potions
    private boolean isPotion;
    private boolean isTippedArrow;
    private Color potionColor;
    private PotionType potionType;

    // Leather.
    private boolean isLeather;
    private boolean isArmor;
    private TrimMaterial trimMaterial;
    private TrimPattern trimPattern;
    private Color armorColor;

    // Banners.
    private boolean isBanner;
    private List<Pattern> patterns;

    // Shields.
    private boolean isShield;

    // Firework.
    private boolean isFirework;
    private boolean isFireworkStar;

    // Enchantments/Flags
    private boolean isDurable;
    private boolean hideFlags;
    private boolean isGlowing;

    protected BaseItemBuilder() {
        this.itemStack = null;
        this.itemMeta = null;
        this.material = Material.AIR;
        this.lore_field = null;

        this.trimMaterial = null;
        this.trimPattern = null;

        this.hasCustomModelData = false;
        this.customModelData = 0;

        this.isHead = false;
        this.profile = null;
        this.texture = "";

        this.isPotion = false;
        this.potionColor = Color.WHITE;
        this.potionType = PotionType.MUNDANE;

        this.isTippedArrow = false;

        this.isLeather = false;
        this.armorColor = Color.WHITE;

        this.damage = 0;

        this.isBanner = false;
        this.isShield = false;
        this.patterns = Collections.emptyList();

        this.isFirework = false;
        this.isFireworkStar = false;

        this.isDurable = false;
        this.isGlowing = false;

        this.hideFlags = false;
    }

    protected BaseItemBuilder(ItemStack itemStack) {
        this.itemStack = itemStack;

        try {
            Class<?> metaClass = this.itemUtils.craftClass("inventory.CraftMetaItem");

            this.lore_field = metaClass.getDeclaredField("lore");
            this.lore_field.setAccessible(true);
        } catch (NoSuchFieldException | ClassNotFoundException exception) {
            this.paper.logger().warn("Failed to make the lore field accessible as it was not found. Perhaps an invalid item was supplied?");
        }

        this.material = itemStack.getType();

        switch (this.material) {
            case PLAYER_HEAD -> this.isHead = true;
            case POTION, SPLASH_POTION -> this.isPotion = true;
            case TIPPED_ARROW -> this.isTippedArrow = true;
            case LEATHER_HELMET, LEATHER_CHESTPLATE, LEATHER_LEGGINGS, LEATHER_BOOTS, LEATHER_HORSE_ARMOR -> this.isLeather = true;
            case SHIELD -> this.isShield = true;
            case FIREWORK_ROCKET -> this.isFirework = true;
            case FIREWORK_STAR -> this.isFireworkStar = true;
        }

        String name = this.material.name();

        this.isArmor = name.endsWith("_HELMET") || name.endsWith("_CHESTPLATE") || name.endsWith("_LEGGINGS") || name.endsWith("_BOOTS");

        this.isBanner = this.material.name().contains("BANNER");

        this.itemMeta = itemStack.hasItemMeta() ? itemStack.getItemMeta() : Bukkit.getServer().getItemFactory().getItemMeta(this.material);
    }

    public Base setDisplayName(Component displayName) {
        this.itemMeta.displayName(displayName.decorationIfAbsent(TextDecoration.ITALIC, TextDecoration.State.FALSE));
        return (Base) this;
    }

    public Base setLore(Component ... lore) {
        return setLore(Arrays.asList(lore));
    }

    public Base setLore(List<Component> lore) {
        List<String> jsonLore = lore.stream().filter(Objects::nonNull).map(this.gson::serialize).toList();

        try {
            this.lore_field.set(this.itemMeta, jsonLore);
        } catch (IllegalAccessException exception) {
            exception.printStackTrace();
        }

        return (Base) this;
    }

    public Base addLore(Consumer<List<Component>> lore) {
        List<Component> components;

        try {
            List<String> jsonLore = (List<String>) this.lore_field.get(this.itemMeta);

            components = (jsonLore == null) ? new ArrayList<>() : jsonLore.stream().map(this.gson::deserialize).collect(Collectors.toList());
        } catch (Exception exception) {
            components = new ArrayList<>();
            exception.printStackTrace();
        }

        lore.accept(components);
        return (Base) this;
    }

    public Base setAmount(int amount) {
        this.itemStack.setAmount(amount);
        return (Base) this;
    }

    public Base addEnchantment(Enchantment enchantment, int level, boolean ignoreLevelRestriction) {
        this.itemMeta.addEnchant(enchantment, level, ignoreLevelRestriction);
        return (Base) this;
    }

    public Base removeEnchantment(Enchantment enchantment) {
        this.itemMeta.removeEnchant(enchantment);
        return (Base) this;
    }

    public Base setEnchantments(HashMap<Enchantment, Integer> enchantments, boolean ignoreLevelRestriction) {
        enchantments.forEach((enchantment, integer) -> this.itemMeta.addEnchant(enchantment, integer, ignoreLevelRestriction));
        return (Base) this;
    }

    public Base addPatterns(List<String> patterns) {
        patterns.forEach(this::addPatterns);
        return (Base) this;
    }

    public Base addPattern(Pattern pattern) {
        this.patterns.add(pattern);
        return (Base) this;
    }

    public Base setPattern(List<Pattern> patterns) {
        this.patterns = patterns;
        return (Base) this;
    }

    public Base addItemFlags(List<String> flags) {
        flags.forEach(flag -> {
            try {
                ItemFlag itemFlag = ItemFlag.valueOf(flag.toUpperCase());

                addItemFlag(itemFlag);
            } catch (Exception exception) {
                this.paper.logger().warn("Failed to add item-flag: " + flag + ". The flag is invalid!");
            }
        });

        return (Base) this;
    }

    public Base setTexture(String texture) {
        this.texture = texture;

        return (Base) this;
    }

    public Base setValue(String material) {
        if (material == null || material.isEmpty()) {
            this.paper.logger().warn("Material cannot be null or empty, Output: " + material + ".");
            this.paper.logger().warn("Please take a screenshot of this before asking for support.");

            return (Base) this;
        }

        String metaData;

        if (this.isPotion || this.isTippedArrow) {
            if (material.contains(";")) {
                String[] section = material.split(";");

                String[] sectionOne = section[0].split(":");
                String[] sectionTwo = section[1].split(":");

                try {
                    this.potionType = PotionType.valueOf(sectionOne[1]);
                } catch (Exception exception) {
                    this.paper.logger().warn("Failed to set potion type " + sectionOne[1] + ". The potion type inputted is invalid.");
                }

                this.potionColor = getColor(sectionTwo[1]);
            }

            return (Base) this;
        }

        if (material.contains(":")) { // Sets the durability or another value option.
            String[] materialSplit = material.split(":");

            material = materialSplit[0];
            metaData = materialSplit[1];

            if (metaData.contains("#")) { // <ID>:<Durability>#<CustomModelData>
                String modelData = metaData.split("#")[1];

                if (isValidInteger(modelData)) {
                    this.hasCustomModelData = true;
                    this.customModelData = Integer.parseInt(modelData);
                }
            }

            metaData = metaData.replace("#" + this.customModelData, "");

            if (isValidInteger(metaData)) { // Value is durability.
                int damage = Integer.parseInt(metaData);

                if (this.itemMeta instanceof Damageable) ((Damageable) this.itemMeta).setDamage(damage);
            } else { // Value is something else.
                if (this.isPotion) {
                    this.potionType = PotionType.valueOf(metaData);

                    if (getColor(metaData) != null) this.potionColor = getColor(metaData);
                }

                if (this.isLeather) this.armorColor = getColor(metaData);
            }
        } else if (material.contains("#")) {
            String[] materialSplit = material.split("#");
            material = materialSplit[0];

            if (isValidInteger(materialSplit[1])) { // Value is a number.
                this.hasCustomModelData = true;
                this.customModelData = Integer.parseInt(materialSplit[1]);
            }
        }

        Material matchedMaterial = Material.matchMaterial(material);

        if (matchedMaterial != null) this.material = matchedMaterial;

        if (this.isArmor) ((ArmorMeta) this.itemMeta).setTrim(new ArmorTrim(this.trimMaterial, this.trimPattern));

        this.itemStack.setType(this.material);

        setItemMeta(this.itemStack.getItemMeta());

        return (Base) this;
    }

    public Base hideFlags(boolean hideFlags) {
        this.hideFlags = hideFlags;
        return (Base) this;
    }

    public Base setGlow(boolean isGlowing) {
        this.isGlowing = isGlowing;
        return (Base) this;
    }

    public Base setTrimMaterial(TrimMaterial trimMaterial) {
        this.trimMaterial = trimMaterial;
        return (Base) this;
    }

    public Base setTrimPattern(TrimPattern trimPattern) {
        this.trimPattern = trimPattern;
        return (Base) this;
    }

    public Base setDurable(boolean isDurable) {
        this.isDurable = isDurable;
        return (Base) this;
    }

    public Base setEffect(FireworkEffect... effects) {
        return setEffect(Arrays.asList(effects));
    }

    public Base setEffect(List<FireworkEffect> effects) {
        if (effects.isEmpty()) return (Base) this;

        if (this.isFireworkStar) {
            FireworkEffectMeta effectMeta = (FireworkEffectMeta) this.getItemMeta();

            effectMeta.setEffect(effects.get(0));
            this.setItemMeta(effectMeta);
        }

        if (this.isFirework) {
            FireworkMeta fireworkMeta = (FireworkMeta) this.getItemMeta();

            fireworkMeta.addEffects(effects);
            this.setItemMeta(fireworkMeta);
        }

        return (Base) this;
    }

    public Base setPower(int power) {
        if (this.isFirework) {
            FireworkMeta fireworkMeta = (FireworkMeta) this.getItemMeta();

            fireworkMeta.setPower(power);

            this.setItemMeta(fireworkMeta);
        }

        return (Base) this;
    }

    public Base setDamage(int damage) {
        this.damage = damage;

        return (Base) this;
    }

    public ItemStack build() {
        if (this.material != Material.AIR) {
            if (this.isHead) {
                // Set the field to accessible.
                exposeField();

                setPlayerTexture(this.texture);
            }

            if (this.isPotion || this.isTippedArrow && (this.potionType != null || this.potionColor != null)) {
                PotionMeta potionMeta = (PotionMeta) this.itemMeta;

                if (this.potionType != null) potionMeta.setBasePotionData(new PotionData(this.potionType));

                if (this.potionColor != null) potionMeta.setColor(this.potionColor);

                this.setItemMeta(potionMeta);
            }

            if (this.itemMeta instanceof Damageable) {
                if (this.damage >= 1) {
                    if (this.damage >= this.itemStack.getType().getMaxDurability()) {
                        ((Damageable) this.itemMeta).setDamage(this.itemStack.getType().getMaxDurability());
                    } else {
                        ((Damageable) this.itemMeta).setDamage(this.damage);
                    }
                }
            }

            if (this.isLeather && this.armorColor != null) {
                LeatherArmorMeta leatherArmorMeta = (LeatherArmorMeta) this.itemMeta;
                leatherArmorMeta.setColor(this.armorColor);
            }

            if (this.isBanner && !this.patterns.isEmpty()) {
                BannerMeta bannerMeta = (BannerMeta) this.itemMeta;
                bannerMeta.setPatterns(this.patterns);
            }

            if (this.isShield && !this.patterns.isEmpty()) {
                BlockStateMeta shieldMeta = (BlockStateMeta) this.itemMeta;
                Banner banner = (Banner) shieldMeta.getBlockState();

                banner.setPatterns(this.patterns);
                banner.update();

                shieldMeta.setBlockState(banner);
            }

            if (this.hasCustomModelData) this.itemMeta.setCustomModelData(this.customModelData);

            if (this.hideFlags) this.itemMeta.addItemFlags(ItemFlag.values());

            this.itemMeta.setUnbreakable(this.isDurable);

            addGlow();
        } else {
            this.paper.logger().warn("Material cannot be AIR.");
        }

        this.itemStack.setItemMeta(this.itemMeta);

        return this.itemStack;
    }

    private void addItemFlag(ItemFlag itemFlag) {
        this.itemMeta.addItemFlags(itemFlag);
    }

    private void setPlayerTexture(String texture) {
        this.texture = texture;

        Player player = Bukkit.getServer().getPlayer(this.texture);

        if (player != null) {
            setOwner(player);
            return;
        }

        if (this.texture.startsWith("http")) {
            setTexture(convert(this.texture), UUID.randomUUID());
            return;
        }

        setTexture(this.texture, UUID.randomUUID());
    }

    private void setOwner(OfflinePlayer player) {
        if (this.itemUtils.isPlayerSkull(this.material)) return;

        SkullMeta skullMeta = (SkullMeta) this.getItemMeta();

        skullMeta.setOwningPlayer(player);

        this.setItemMeta(skullMeta);
    }

    private void addGlow() {
        if (this.isGlowing) {
            if (this.itemMeta.hasEnchants()) return;

            this.itemMeta.addEnchant(Enchantment.LUCK, 1, false);

            this.setItemMeta(this.itemMeta);
        }
    }

    private void exposeField() {
        if (this.itemUtils.isPlayerSkull(this.material)) return;

        Field field;

        try {
            SkullMeta skullMeta = (SkullMeta) this.itemUtils.skull().getItemMeta();
            field = skullMeta.getClass().getDeclaredField("profile");

            field.setAccessible(true);
        } catch (NoSuchFieldException exception) {
            this.paper.logger().warn("Failed to make the meta field for profile accessible as it was not found. Perhaps an invalid item meta or field supplied?");

            field = null;
        }

        this.profile = field;
    }

    private boolean isValidInteger(String value) {
        try {
            Integer.parseInt(value);
        } catch (NumberFormatException exception) {
            return false;
        }

        return true;
    }

    private Color getColor(String color) {
        if (color != null) {
            switch (color.toUpperCase()) {
                case "AQUA" -> {
                    return Color.AQUA;
                }
                case "BLACK" -> {
                    return Color.BLACK;
                }
                case "BLUE" -> {
                    return Color.BLUE;
                }
                case "FUCHSIA" -> {
                    return Color.FUCHSIA;
                }
                case "GRAY" -> {
                    return Color.GRAY;
                }
                case "GREEN" -> {
                    return Color.GREEN;
                }
                case "LIME" -> {
                    return Color.LIME;
                }
                case "MAROON" -> {
                    return Color.MAROON;
                }
                case "NAVY" -> {
                    return Color.NAVY;
                }
                case "OLIVE" -> {
                    return Color.OLIVE;
                }
                case "ORANGE" -> {
                    return Color.ORANGE;
                }
                case "PURPLE" -> {
                    return Color.PURPLE;
                }
                case "RED" -> {
                    return Color.RED;
                }
                case "SILVER" -> {
                    return Color.SILVER;
                }
                case "TEAL" -> {
                    return Color.TEAL;
                }
                case "WHITE" -> {
                    return Color.WHITE;
                }
                case "YELLOW" -> {
                    return Color.YELLOW;
                }
            }

            try {
                String[] rgb = color.split(",");
                return Color.fromRGB(Integer.parseInt(rgb[0]), Integer.parseInt(rgb[1]), Integer.parseInt(rgb[2]));
            } catch (Exception ignore) {}
        }

        return null;
    }

    private void addPatterns(String stringPattern) {
        try {
            String[] split = stringPattern.split(":");

            for (PatternType pattern : PatternType.values()) {

                if (split[0].equalsIgnoreCase(pattern.name()) || split[0].equalsIgnoreCase(pattern.getIdentifier())) {
                    DyeColor color = getDyeColor(split[1]);

                    if (color != null) this.addPattern(new Pattern(color, pattern));

                    break;
                }
            }
        } catch (Exception ignored) {}
    }

    public DyeColor getDyeColor(String color) {
        if (color != null) {
            try {
                return DyeColor.valueOf(color.toUpperCase());
            } catch (Exception exception) {
                try {
                    String[] rgb = color.split(",");
                    return DyeColor.getByColor(Color.fromRGB(Integer.parseInt(rgb[0]), Integer.parseInt(rgb[1]), Integer.parseInt(rgb[2])));
                } catch (Exception ignore) {}
            }
        }

        return null;
    }

    private void setTexture(String texture, UUID uuid) {
        if (this.itemUtils.isPlayerSkull(this.material)) return;

        if (this.profile == null) return;

        SkullMeta skullMeta = (SkullMeta) this.itemMeta;
        GameProfile gameProfile = new GameProfile(uuid, null);

        gameProfile.getProperties().put("textures", new Property("textures", texture));

        try {
            this.profile.set(skullMeta, gameProfile);
        } catch (Exception exception) {
            this.paper.logger().warn("Failed to set the meta & game profile. Perhaps an invalid texture?");
            this.paper.logger().warn("Current Input: " + texture + ".");
        }

        setItemMeta(skullMeta);
    }

    private String convert(String url) {
        URL actualLink;

        try {
            actualLink = new URL(url);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }

        String encode = "{\"textures\":{\"SKIN\":{\"url\":\"" + actualLink + "\"}}}";

        return Base64.getEncoder().encodeToString(encode.getBytes());
    }

    // Protected getters for extended builders.
    protected ItemStack getItemStack() {
        return this.itemStack;
    }

    protected void setItemStack(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    protected ItemMeta getItemMeta() {
        return this.itemMeta;
    }

    protected void setItemMeta(ItemMeta itemMeta) {
        this.itemMeta = itemMeta;
    }
}