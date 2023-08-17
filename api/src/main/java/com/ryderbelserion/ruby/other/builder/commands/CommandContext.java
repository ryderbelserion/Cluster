package com.ryderbelserion.ruby.other.builder.commands;

import com.ryderbelserion.ruby.minecraft.RubyImpl;
import com.ryderbelserion.ruby.other.registry.RubyProvider;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.permission.PermissionChecker;
import net.kyori.adventure.text.Component;
import java.util.List;
import java.util.UUID;

public class CommandContext {

    private final RubyImpl ruby = RubyProvider.get();

    private final List<String> args;
    private final Audience audience;
    private final String label;

    private UUID uuid;

    public CommandContext(Audience audience, String label, List<String> args) {
        this.audience = audience;
        this.label = label;
        this.args = args;
    }

    public void reply(String message) {
        if (empty(message)) return;

        Component component = this.ruby.adventure().parse(message);

        this.audience.sendMessage(component);
    }

    public void send(Component component) {
        if (component == null) return;

        this.audience.sendMessage(component);
    }

    public void send(Audience audience, String message) {
        if (empty(message)) return;

        Component component = this.ruby.adventure().parse(message);

        audience.sendMessage(component);
    }

    public void send(Audience audience, Component component) {
        if (component == null) return;

        audience.sendMessage(component);
    }

    private boolean empty(String message) {
        return message.isBlank() || message.isEmpty();
    }

    public Audience sender() {
        return this.audience;
    }

    public boolean isPlayer() {
        return getPlayer() != null;
    }

    public Audience getPlayer() {
        return this.ruby.audience().player(this.uuid);
    }

    public boolean hasPermission(String permission) {
        if (isPlayer()) return getPlayer().get(PermissionChecker.POINTER).map(checker -> checker.test(permission)).orElse(false);

        // Is not a player so always return true!
        return true;
    }

    public void uuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getLabel() {
        return this.label;
    }

    public List<String> getArgs() {
        return this.args;
    }

    public void removeArgAt(int point) {
        if (this.args.isEmpty()) {
            switch (ruby.platform()) {
                case PAPER, SPIGOT, FABRIC -> this.ruby.logger().error("You did not supply any args to check.");
                case OTHER -> System.out.println(this.ruby.prefix() + "You did not supply any args to check.");
            }

            return;
        }

        this.args.remove(point);
    }
}