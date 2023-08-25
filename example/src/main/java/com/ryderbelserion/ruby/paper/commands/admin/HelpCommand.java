package com.ryderbelserion.ruby.paper.commands.admin;

import com.ryderbelserion.ruby.paper.plugin.commands.PaperCommandContext;
import com.ryderbelserion.ruby.paper.plugin.commands.PaperCommandEngine;
import com.ryderbelserion.ruby.paper.plugin.commands.reqs.PaperRequirementsBuilder;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

public class HelpCommand extends PaperCommandEngine {

    public HelpCommand() {
        super("help", "The help command for crafty", "crafty help", Collections.emptyList());

        this.paperRequirements = new PaperRequirementsBuilder().isPlayer(false).withPermission(new Permission("crafty.help", PermissionDefault.TRUE)).build();
    }

    @Override
    public void perform(PaperCommandContext context, String[] args) {
        context.reply("<red>This is a help command</red>");
    }

    @Override
    public @NotNull List<String> tabComplete(@NotNull CommandSender sender, @NotNull String alias, @NotNull String[] args) throws IllegalArgumentException {
        return handleTabComplete(sender, List.of(args));
    }
}