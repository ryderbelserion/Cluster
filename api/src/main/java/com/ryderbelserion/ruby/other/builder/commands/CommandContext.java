package com.ryderbelserion.ruby.other.builder.commands;

import com.ryderbelserion.ruby.minecraft.RubyPlugin;
import com.ryderbelserion.ruby.minecraft.plugin.registry.RubyProvider;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.permission.PermissionChecker;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import java.util.List;
import java.util.UUID;

public class CommandContext {

    private final @NotNull RubyPlugin plugin = RubyProvider.get();

    private final List<String> args;
    private final Audience audience;
    private String label;

    private UUID uuid;

    public CommandContext(Audience audience, String label, List<String> args) {
        this.audience = audience;
        this.label = label;
        this.args = args;
    }

    public void reply(String message) {
        if (empty(message)) return;

        Component component = this.plugin.getAdventure().parse(message);

        this.audience.sendMessage(component);
    }

    public void send(Component component) {
        if (component == null) return;

        this.audience.sendMessage(component);
    }

    public void send(Audience audience, String message) {
        if (empty(message)) return;

        Component component = this.plugin.getAdventure().parse(message);

        audience.sendMessage(component);
    }

    public void send(Audience audience, Component component) {
        if (component == null) return;

        audience.sendMessage(component);
    }

    private boolean empty(String message) {
        return message.isBlank() || message.isEmpty();
    }

    public Audience getSender() {
        return this.audience;
    }

    public boolean isPlayer() {
        return getPlayer() != null;
    }

    public Audience getPlayer() {
        return this.plugin.getAudience().player(this.uuid);
    }

    public boolean hasPermission(String permission) {
        if (isPlayer()) return getPlayer().get(PermissionChecker.POINTER).map(checker -> checker.test(permission)).orElse(false);

        // Is not a player so always return true!
        return true;
    }

    public void setUUID(UUID uuid) {
        this.uuid = uuid;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getLabel() {
        return this.label;
    }

    public List<String> getArgs() {
        return this.args;
    }
}