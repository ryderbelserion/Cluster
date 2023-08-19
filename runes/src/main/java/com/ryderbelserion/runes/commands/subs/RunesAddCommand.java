package com.ryderbelserion.runes.commands.subs;

import com.ryderbelserion.ruby.other.builder.commands.args.Argument;
import com.ryderbelserion.ruby.paper.plugin.builder.commands.PaperCommandContext;
import com.ryderbelserion.ruby.paper.plugin.builder.commands.PaperCommandEngine;
import com.ryderbelserion.ruby.paper.plugin.builder.commands.args.types.CustomArgument;
import java.util.Collections;
import java.util.List;

public class RunesAddCommand extends PaperCommandEngine {

    public RunesAddCommand() {
        super("add", "Adds commands", "runes:commands add", Collections.emptyList());

        addRequiredArgument(new Argument("custom", 0, new CustomArgument(List.of("hello", "goodbye"))));
    }

    @Override
    public void perform(PaperCommandContext context, String[] args) {
        String whatever = args[1];

        context.reply("<green>Here is your args 0 " + whatever);
    }
}