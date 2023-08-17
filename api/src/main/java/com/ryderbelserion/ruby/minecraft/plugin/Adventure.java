package com.ryderbelserion.ruby.minecraft.plugin;

import com.ryderbelserion.ruby.minecraft.RubyImpl;
import com.ryderbelserion.ruby.minecraft.plugin.registry.RubyProvider;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.jetbrains.annotations.NotNull;

public class Adventure {

    private @NotNull RubyImpl ruby() {
        return RubyProvider.get();
    }

    private MiniMessage adventure() {
        return MiniMessage.miniMessage();
    }

    public Component parse(String message) {
        return adventure().deserialize(message).decorationIfAbsent(TextDecoration.ITALIC, TextDecoration.State.FALSE);
    }

    public void console(String message) {
        ruby().audience().console().sendMessage(parse(message));
    }

    public void player(Audience audience, String message) {
        audience.sendMessage(parse(message));
    }

    public void hover(Audience audience, String message, String text, String value, ClickEvent.Action action) {
        Component component = parse(message)
                .hoverEvent(HoverEvent.showText(parse(text)))
                .clickEvent(ClickEvent.clickEvent(action, value));

        audience.sendMessage(component);
    }

    public void hover(Audience audience, String message, String text, String button, String value, ClickEvent.Action action) {
        Component component = parse(message)
                .append(parse(button).hoverEvent(HoverEvent.showText(parse(text))))
                .clickEvent(ClickEvent.clickEvent(action, value));

        audience.sendMessage(component);
    }
}