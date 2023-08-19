package com.ryderbelserion.runes.commands.subs;

import com.ryderbelserion.ruby.paper.plugin.builder.commands.PaperCommandContext;
import com.ryderbelserion.ruby.paper.plugin.builder.commands.PaperCommandEngine;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import java.util.Collections;
import java.util.List;

public class RunesBaseCommand extends PaperCommandEngine {

    public RunesBaseCommand() {
        super("commands", "Handles commands", "runes:commands", Collections.emptyList());

        addCommand(new RunesAddCommand());
        addCommand(new RunesRemoveCommand());
    }

    @Override
    public void perform(PaperCommandContext context, String[] args) {

    }

    @Override
    public @NotNull List<String> tabComplete(@NotNull CommandSender sender, @NotNull String alias, @NotNull String[] args) throws IllegalArgumentException {
        return handleTabComplete(List.of(args));
    }
}