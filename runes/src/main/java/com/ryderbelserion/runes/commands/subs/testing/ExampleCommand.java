package com.ryderbelserion.runes.commands.subs.testing;

import com.ryderbelserion.ruby.paper.plugin.builder.commands.PaperCommandContext;
import com.ryderbelserion.ruby.paper.plugin.builder.commands.PaperCommandEngine;
import com.ryderbelserion.runes.commands.subs.testing.subs.ExampleCommandThree;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import java.util.Collections;
import java.util.List;

public class ExampleCommand extends PaperCommandEngine {

    public ExampleCommand() {
        super("example", "example", "runes:example", Collections.emptyList());

        addCommand(new ExampleCommandThree());
    }

    @Override
    public void perform(PaperCommandContext context, String[] args) {
        context.reply("<gold>Example Base");
    }

    @Override
    public @NotNull List<String> tabComplete(@NotNull CommandSender sender, @NotNull String alias, @NotNull String[] args) throws IllegalArgumentException {
        return handleTabComplete(List.of(args));
    }
}