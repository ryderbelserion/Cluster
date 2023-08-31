package com.ryderbelserion.cluster.bukkit.utils;

import net.md_5.bungee.api.ChatColor;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SuppressWarnings("deprecation")
public class LegacyUtils {

    private Pattern hexPattern;

    public String parse(String message) {
        Matcher matcher = this.hexPattern.matcher(message);
        StringBuilder buffer = new StringBuilder();

        while (matcher.find()) {
            matcher.appendReplacement(buffer, ChatColor.of(matcher.group()).toString());
        }

        return ChatColor.translateAlternateColorCodes('&', matcher.appendTail(buffer).toString());
    }

    public void setHexPattern(Pattern hexPattern) {
        this.hexPattern = hexPattern;
    }
}