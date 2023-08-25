package com.ryderbelserion.ruby.paper.commands;

import com.ryderbelserion.ruby.paper.commands.admin.HelpCommand;
import com.ryderbelserion.ruby.paper.commands.admin.ReloadCommand;
import com.ryderbelserion.ruby.paper.plugin.commands.PaperCommandContext;
import com.ryderbelserion.ruby.paper.plugin.commands.PaperCommandEngine;
import com.ryderbelserion.ruby.paper.plugin.commands.PaperCommandHelpEntry;
import com.ryderbelserion.ruby.paper.plugin.commands.reqs.PaperRequirementsBuilder;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;
import org.jetbrains.annotations.NotNull;
import java.util.Collections;
import java.util.List;

public class CraftyCommand extends PaperCommandEngine {

    public CraftyCommand() {
        super("crafty", "The base command for crafty", "crafty", Collections.emptyList());

        addCommand(new ReloadCommand(), false);
        addCommand(new HelpCommand(), false);

        this.paperRequirements = new PaperRequirementsBuilder().isPlayer(false).withPermission(new Permission("crafty.base", PermissionDefault.TRUE)).build();
    }

    @Override
    public void perform(PaperCommandContext context, String[] args) {
        PaperCommandHelpEntry helpEntry = new PaperCommandHelpEntry();

        helpEntry.setPage(1);

        helpEntry.showHelp(context);
    }

    @Override
    public @NotNull List<String> tabComplete(@NotNull CommandSender sender, @NotNull String alias, @NotNull String[] args) throws IllegalArgumentException {
        return handleTabComplete(sender, List.of(args));
    }
}