package com.ryderbelserion.ruby.other.builder;

import com.ryderbelserion.ruby.minecraft.RubyPlugin;
import com.ryderbelserion.ruby.minecraft.plugin.registry.RubyProvider;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import org.jetbrains.annotations.NotNull;

public class ComponentBuilder {

    private final @NotNull RubyPlugin plugin = RubyProvider.get();

    private final TextComponent.@NotNull Builder builder = Component.text();

    private final FancyComponentBuilder fancyComponentBuilder;

    public ComponentBuilder() {
        this.fancyComponentBuilder = new FancyComponentBuilder();
    }

    private String message;

    public void setMessage(String message) {
        this.message = message;
    }

    public ComponentBuilder append(Component component) {
        this.builder.append(component);

        return this;
    }

    public Component parse(String value) {
        return this.plugin.getAdventure().parse(value);
    }

    public ComponentBuilder hover(String text) {
        this.builder.hoverEvent(HoverEvent.showText(parse(text)));

        return this;
    }

    public ComponentBuilder click(ClickEvent.Action action, String text) {
        this.builder.clickEvent(ClickEvent.clickEvent(action, text));

        return this;
    }

    public FancyComponentBuilder getFancyComponentBuilder() {
        return this.fancyComponentBuilder;
    }

    public @NotNull TextComponent build() {
        Component message = parse(this.message);

        //if (this.fancyComponentBuilder.fancy() != null) {
        //    return this.builder.append(message).append(this.fancyComponentBuilder.fancy()).build();
        //}

        return this.builder.append(message).build();
    }
}