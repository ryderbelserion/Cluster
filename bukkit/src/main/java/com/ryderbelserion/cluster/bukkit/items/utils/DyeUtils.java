package com.ryderbelserion.cluster.bukkit.items.utils;

import org.bukkit.Color;
import org.bukkit.DyeColor;

public class DyeUtils {

    public static Color getColor(String color) {
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

    public static DyeColor getDyeColor(String color) {
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
}