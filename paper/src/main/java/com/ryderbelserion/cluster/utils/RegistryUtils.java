package com.ryderbelserion.cluster.utils;

import com.ryderbelserion.cluster.Cluster;
import com.ryderbelserion.cluster.ClusterProvider;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.banner.PatternType;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.meta.trim.TrimMaterial;
import org.bukkit.inventory.meta.trim.TrimPattern;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;
import java.util.logging.Logger;

public class RegistryUtils {

    private static final Cluster server = ClusterProvider.get();
    private static final Logger logger = server.getLogger();

    /**
     * Get a material from the registry.
     * 
     * @param value the value to check.
     * @return the material or null if not found.
     */
    public static Material getMaterial(String value) {
        try {
            return Registry.MATERIAL.get(getKey(value));
        } catch (Exception exception) {
            logger.warning(value + " is an invalid material.");

            return null;
        }
    }

    /**
     * Get a sound from the registry.
     *
     * @param value the value to check.
     * @return the sound or null if not found.
     */
    public static Sound getSound(String value) {
        try {
            return Registry.SOUNDS.get(getKey(value));
        } catch (Exception exception) {
            logger.warning(value + " is an invalid sound.");

            return null;
        }
    }

    /**
     * Get an enchantment from the registry.
     *
     * @param value the value to check.
     * @return the enchantment or null if not found.
     */
    public static Enchantment getEnchantment(String value) {
        try {
            return Registry.ENCHANTMENT.get(getKey(value));
        } catch (Exception exception) {
            logger.warning(value + " is an invalid enchantment.");

            return null;
        }
    }

    /**
     * Get a trim pattern from the registry.
     *
     * @param value the value to check.
     * @return the trim pattern or null if not found.
     */
    public static TrimPattern getTrimPattern(String value) {
        try {
            return Registry.TRIM_PATTERN.get(getKey(value));
        } catch (Exception exception) {
            logger.warning(value + " is an invalid trim pattern.");

            return null;
        }
    }

    /**
     * Get a trim material from the registry.
     *
     * @param value the value to check.
     * @return the trim material or null if not found.
     */
    public static TrimMaterial getTrimMaterial(String value) {
        try {
            return Registry.TRIM_MATERIAL.get(getKey(value));
        } catch (Exception exception) {
            logger.warning(value + " is an invalid trim material.");

            return null;
        }
    }

    /**
     * Get a potion type from the registry.
     *
     * @param value the value to check.
     * @return the potion type or null if not found.
     */
    public static PotionType getPotionType(String value) {
        try {
            return Registry.POTION.get(getKey(value));
        } catch (Exception exception) {
            logger.warning(value + " is an invalid potion type.");

            return null;
        }
    }

    /**
     * Get a potion effect type from the registry.
     *
     * @param value the value to check.
     * @return the potion effect type or null if not found.
     */
    public static PotionEffectType getPotionEffect(String value) {
        try {
            return Registry.POTION_EFFECT_TYPE.get(getKey(value));
        } catch (Exception exception) {
            logger.warning(value + " is an invalid potion effect type.");

            return null;
        }
    }

    /**
     * Get a particle type from the registry.
     *
     * @param value the value to check.
     * @return the particle type or null if not found.
     */
    public static Particle getParticleType(String value) {
        try {
            return Registry.PARTICLE_TYPE.get(getKey(value));
        } catch (Exception exception) {
            logger.warning(value + " is an invalid particle type.");

            return null;
        }
    }

    /**
     * Get a banner pattern from the registry.
     *
     * @param value the value to check.
     * @return the banner pattern or null if not found.
     */
    public static PatternType getPatternType(String value) {
        try {
            return Registry.BANNER_PATTERN.get(getKey(value));
        } catch (Exception exception) {
            logger.warning(value + " is an invalid banner type.");

            return null;
        }
    }

    /**
     * Get an attribute from the registry.
     *
     * @param value the value to check.
     * @return the attribute or null if not found.
     */
    public static Attribute getAttribute(String value) {
        try {
            return Registry.ATTRIBUTE.get(getKey(value));
        } catch (Exception exception) {
            logger.warning(value + " is an invalid attribute.");

            return null;
        }
    }

    /**
     * Check the namespace key
     *
     * @param value the value to check.
     * @return the namespace key.
     */
    private static NamespacedKey getKey(String value) {
        return NamespacedKey.minecraft(value);
    }
}