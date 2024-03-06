package com.ryderbelserion.cluster.utils;

import com.ryderbelserion.cluster.ClusterProvider;
import com.ryderbelserion.cluster.platform.ClusterServer;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Particle;
import org.bukkit.Registry;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.banner.PatternType;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.meta.trim.TrimMaterial;
import org.bukkit.inventory.meta.trim.TrimPattern;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;
import java.util.logging.Logger;

public class ItemUtils {

    private static final ClusterServer server = ClusterProvider.get().getServer();
    private static final Logger logger = server.getLogger();

    public static Material getMaterial(String value) {
        try {
            return Registry.MATERIAL.get(getKey(value));
        } catch (Exception exception) {
            logger.warning(value + " is an invalid material.");

            return null;
        }
    }

    public static Enchantment getEnchantment(String value) {
        try {
            return Registry.ENCHANTMENT.get(getKey(value));
        } catch (Exception exception) {
            logger.warning(value + " is an invalid enchantment.");

            return null;
        }
    }

    public static TrimPattern getTrimPattern(String value) {
        try {
            return Registry.TRIM_PATTERN.get(getKey(value));
        } catch (Exception exception) {
            logger.warning(value + " is an invalid trim pattern.");

            return null;
        }
    }

    public static TrimMaterial getTrimMaterial(String value) {
        try {
            return Registry.TRIM_MATERIAL.get(getKey(value));
        } catch (Exception exception) {
            logger.warning(value + " is an invalid trim material.");

            return null;
        }
    }

    public static PotionType getPotionType(String value) {
        try {
            return Registry.POTION.get(getKey(value));
        } catch (Exception exception) {
            logger.warning(value + " is an invalid potion type.");

            return null;
        }
    }

    public static PotionEffectType getPotionEffect(String value) {
        try {
            return Registry.POTION_EFFECT_TYPE.get(getKey(value));
        } catch (Exception exception) {
            logger.warning(value + " is an invalid potion effect type.");

            return null;
        }
    }

    public static Particle getParticleType(String value) {
        try {
            return Registry.PARTICLE_TYPE.get(getKey(value));
        } catch (Exception exception) {
            logger.warning(value + " is an invalid particle type.");

            return null;
        }
    }

    public static PatternType getPatternType(String value) {
        try {
            return Registry.BANNER_PATTERN.get(getKey(value));
        } catch (Exception exception) {
            logger.warning(value + " is an invalid banner type.");

            return null;
        }
    }

    public static Attribute getAttribute(String value) {
        try {
            return Registry.ATTRIBUTE.get(getKey(value));
        } catch (Exception exception) {
            logger.warning(value + " is an invalid attribute.");

            return null;
        }
    }

    private static NamespacedKey getKey(String value) {
        return NamespacedKey.minecraft(value);
    }
}