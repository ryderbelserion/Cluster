package com.ryderbelserion.cluster.api.utils;

import com.ryderbelserion.cluster.api.AbstractPlugin;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;

public class ColorUtils {

    private static MiniMessage adventure() {
        return MiniMessage.miniMessage();
    }

    public static Component parse(String message) {
        return adventure().deserialize(message).decorationIfAbsent(TextDecoration.ITALIC, TextDecoration.State.FALSE);
    }

    public static void console(String message) {
        AbstractPlugin.getConsole().sendMessage(parse(message));
    }

    public static void player(Audience audience, String message) {
        audience.sendMessage(parse(message));
    }
}