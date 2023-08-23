package com.ryderbelserion.ruby.minecraft.utils;

import com.ryderbelserion.ruby.minecraft.RubyPlugin;
import com.ryderbelserion.ruby.minecraft.plugin.registry.RubyProvider;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.jetbrains.annotations.NotNull;

public class AdvUtil {

    private final @NotNull RubyPlugin plugin = RubyProvider.get();

    private MiniMessage adventure() {
        return MiniMessage.miniMessage();
    }

    public Component parse(String message) {
        return adventure().deserialize(message).decorationIfAbsent(TextDecoration.ITALIC, TextDecoration.State.FALSE);
    }

    public void console(String message) {
        this.plugin.getAudience().sendMessage(parse(message));
    }

    public void player(Audience audience, String message) {
        audience.sendMessage(parse(message));
    }
}