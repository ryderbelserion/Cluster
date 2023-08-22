package com.ryderbelserion.ruby.paper.plugin.builder.commands.reqs;

import com.ryderbelserion.ruby.other.builder.commands.provider.LocaleProvider;
import com.ryderbelserion.ruby.paper.PaperPlugin;
import com.ryderbelserion.ruby.paper.plugin.builder.commands.PaperCommandContext;
import com.ryderbelserion.ruby.paper.plugin.registry.PaperProvider;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import org.jetbrains.annotations.NotNull;

public class PaperRequirements {

    private final @NotNull PaperPlugin plugin = PaperProvider.get();

    private final @NotNull LocaleProvider locale = this.plugin.getLocaleProvider();

    private final boolean isPlayer;
    private final Permission permission;
    private final String rawPermission;

    public PaperRequirements(boolean isPlayer, Permission permission, String rawPermission) {
        this.isPlayer = isPlayer;

        this.permission = permission;

        this.rawPermission = rawPermission;
    }

    public boolean checkRequirements(PaperCommandContext context, boolean notifySender) {
        if (isPlayer && !context.isPlayer()) {
            if (notifySender) context.reply(this.locale.notPlayer());

            return false;
        }

        if (context.getSender() instanceof ConsoleCommandSender) return true;

        Player player = context.getPlayer();

        if (this.permission != null && !player.hasPermission(this.permission)
        || (this.rawPermission != null && !player.hasPermission(this.rawPermission))) {
            if (notifySender) context.reply(this.locale.noPermission());

            return false;
        }

        return true;
    }
}