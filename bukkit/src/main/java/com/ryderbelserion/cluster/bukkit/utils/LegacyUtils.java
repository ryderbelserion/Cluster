package com.ryderbelserion.cluster.bukkit.utils;

import net.md_5.bungee.api.ChatColor;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LegacyUtils {

    private static final Pattern hex_pattern = Pattern.compile("#[a-fA-F\\d]{6}");

    public static String color(String message) {
        Matcher matcher = hex_pattern.matcher(message);
        StringBuilder buffer = new StringBuilder();

        while (matcher.find()) {
            matcher.appendReplacement(buffer, net.md_5.bungee.api.ChatColor.of(matcher.group()).toString());
        }

        return ChatColor.translateAlternateColorCodes('&', matcher.appendTail(buffer).toString());
    }
}