package com.ryderbelserion.runes.commands.subs.testing;

import com.ryderbelserion.ruby.paper.plugin.builder.commands.PaperCommandContext;
import com.ryderbelserion.ruby.paper.plugin.builder.commands.PaperCommandEngine;
import java.util.Collections;

public class ExampleCommandTwo extends PaperCommandEngine {

    public ExampleCommandTwo() {
        super("exampletwo", "exampletwo", "runes:exampletwo", Collections.emptyList());
    }

    @Override
    public void perform(PaperCommandContext context, String[] args) {
        context.reply("<green>Example Two");
    }
}