package com.ryderbelserion.runes.commands.subs.testing.subs;

import com.ryderbelserion.ruby.paper.plugin.builder.commands.PaperCommandContext;
import com.ryderbelserion.ruby.paper.plugin.builder.commands.PaperCommandEngine;
import java.util.Collections;

public class ExampleCommandThree extends PaperCommandEngine {

    public ExampleCommandThree() {
        super("three", "three", "runes:example three", Collections.emptyList());
    }

    @Override
    public void perform(PaperCommandContext context, String[] args) {
        context.reply("<red>Example Three");
    }
}