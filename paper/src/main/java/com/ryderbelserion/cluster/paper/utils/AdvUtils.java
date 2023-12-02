package com.ryderbelserion.cluster.paper.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;

public class AdvUtils {

    public static Component parse(String message) {
        return MiniMessage.miniMessage().deserialize(message);
    }

    public static Component parse(String message, TextDecoration.State state) {
        return parse(message).decorationIfAbsent(TextDecoration.ITALIC, state);
    }
}