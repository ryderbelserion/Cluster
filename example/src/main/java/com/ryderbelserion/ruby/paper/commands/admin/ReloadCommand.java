package com.ryderbelserion.crafty.paper.commands.admin;

import com.ryderbelserion.ruby.paper.plugin.commands.PaperCommandContext;
import com.ryderbelserion.ruby.paper.plugin.commands.PaperCommandEngine;
import com.ryderbelserion.ruby.paper.plugin.commands.reqs.PaperRequirementsBuilder;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;
import org.jetbrains.annotations.NotNull;
import java.util.Collections;
import java.util.List;

public class ReloadCommand extends PaperCommandEngine {

    public ReloadCommand() {
        super("reload", "The reload crafty for crafty", "crafty reload", Collections.emptyList());

        this.paperRequirements = new PaperRequirementsBuilder().isPlayer(false).withPermission(new Permission("crafty.reload", PermissionDefault.FALSE)).build();
    }

    @Override
    public void perform(PaperCommandContext context, String[] args) {
        context.reply("<red>This is a reload command</red>");
    }

    @Override
    public @NotNull List<String> tabComplete(@NotNull CommandSender sender, @NotNull String alias, @NotNull String[] args) throws IllegalArgumentException {
        return handleTabComplete(sender, List.of(args));
    }
}