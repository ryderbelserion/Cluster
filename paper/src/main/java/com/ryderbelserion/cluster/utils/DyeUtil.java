package com.ryderbelserion.cluster.utils;

import com.ryderbelserion.cluster.Cluster;
import com.ryderbelserion.cluster.ClusterProvider;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Color;
import org.bukkit.DyeColor;
import java.util.HashMap;
import java.util.Map;

public class DyeUtil {

    private static final Map<String, Color> colors = createMap();

    private static Map<String, Color> createMap() {
        Map<String, Color> map = new HashMap<>();

        map.put("AQUA", Color.AQUA);
        map.put("BLACK", Color.BLACK);
        map.put("BLUE", Color.BLUE);
        map.put("FUCHSIA", Color.FUCHSIA);
        map.put("GRAY", Color.GRAY);
        map.put("GREEN", Color.GREEN);
        map.put("LIME", Color.LIME);
        map.put("MAROON", Color.MAROON);
        map.put("NAVY", Color.NAVY);
        map.put("OLIVE", Color.OLIVE);
        map.put("ORANGE", Color.ORANGE);
        map.put("PURPLE", Color.PURPLE);
        map.put("RED", Color.RED);
        map.put("SILVER", Color.SILVER);
        map.put("TEAL", Color.TEAL);
        map.put("WHITE", Color.WHITE);
        map.put("YELLOW", Color.YELLOW);

        return map;
    }

    public static Color getColor(String color) {
        if (color == null || color.isBlank()) return null;

        Color mappedColor = colors.get(color.toUpperCase());

        if (mappedColor != null) return mappedColor;

        try {
            String[] rgb = color.split(",");

            if (rgb.length != 3) return null;

            int red = Integer.parseInt(rgb[0]);
            int green = Integer.parseInt(rgb[1]);
            int blue = Integer.parseInt(rgb[2]);
            return Color.fromRGB(red, green, blue);
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException ignore) {}

        return null;
    }

    public static TextColor getTextColor(String color) {
        if (color == null || color.isBlank()) return null;

        try {
            String[] rgb = color.split(",");

            if (rgb.length != 3) return null;

            int red = Integer.parseInt(rgb[0]);
            int green = Integer.parseInt(rgb[1]);
            int blue = Integer.parseInt(rgb[2]);
            return TextColor.color(red, green, blue);
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException ignore) {}

        return null;
    }

    public static DyeColor getDyeColor(String color) {
        if (color == null || color.isBlank()) {
            Cluster server = ClusterProvider.get();

            if (server.isLogging()) server.getLogger().warning(color + " is not a valid color.");

            return null;
        }

        try {
            return DyeColor.valueOf(color.toUpperCase());
        } catch (Exception exception) {
            String[] rgb = color.split(",");

            if (rgb.length != 3) return null;

            try {
                int red = Integer.parseInt(rgb[0]);
                int green = Integer.parseInt(rgb[1]);
                int blue = Integer.parseInt(rgb[2]);
                return DyeColor.getByColor(Color.fromRGB(red, green, blue));
            } catch (Exception ignore) {}
        }

        return null;
    }
}