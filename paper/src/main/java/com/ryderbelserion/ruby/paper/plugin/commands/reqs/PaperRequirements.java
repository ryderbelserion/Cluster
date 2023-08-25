package com.ryderbelserion.ruby.paper.plugin.commands.reqs;

import com.ryderbelserion.ruby.commands.CommandHelpProvider;
import com.ryderbelserion.ruby.paper.PaperPlugin;
import com.ryderbelserion.ruby.paper.plugin.commands.PaperCommandContext;
import com.ryderbelserion.ruby.paper.plugin.registry.PaperProvider;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import org.jetbrains.annotations.NotNull;

public class PaperRequirements {

    private final @NotNull PaperPlugin plugin = PaperProvider.get();

    private final @NotNull CommandHelpProvider locale = this.plugin.getHelpProvider();

    private final boolean isPlayer;
    private final Permission permission;
    private final String rawPermission;
    private PaperRequirementsBuilder builder;

    public PaperRequirements(boolean isPlayer, Permission permission, String rawPermission, PaperRequirementsBuilder builder) {
        this.isPlayer = isPlayer;

        this.permission = permission;

        this.rawPermission = rawPermission;

        if (builder != null) this.builder = builder;
    }

    public boolean checkRequirements(PaperCommandContext context, boolean notifySender) {
        if (this.isPlayer && !context.isPlayer()) {
            if (notifySender) context.reply(this.locale.notPlayer());

            return false;
        }

        if (context.getSender() instanceof ConsoleCommandSender) return true;

        Player player = context.getPlayer();

        if (this.permission != null && !player.hasPermission(this.permission) || this.rawPermission != null && !player.hasPermission(this.rawPermission)) {
            if (notifySender) {
                if (this.permission != null) {
                    context.reply(this.locale.noPermission().replaceAll("\\{permission}", this.permission.getName()));
                } else {
                    context.reply(this.locale.noPermission().replaceAll("\\{permission}", this.rawPermission));
                }
            }

            return false;
        }

        return true;
    }

    public PaperRequirementsBuilder getRequirementsBuilder() {
        return this.builder;
    }
}