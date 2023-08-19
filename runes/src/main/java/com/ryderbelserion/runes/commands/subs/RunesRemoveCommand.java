package com.ryderbelserion.runes.commands.subs;

import com.ryderbelserion.ruby.paper.plugin.builder.commands.PaperCommandContext;
import com.ryderbelserion.ruby.paper.plugin.builder.commands.PaperCommandEngine;
import java.util.Collections;

public class RunesRemoveCommand extends PaperCommandEngine {

    public RunesRemoveCommand() {
        super("remove", "Removes commands", "runes:commands remove", Collections.emptyList());
    }

    @Override
    public void perform(PaperCommandContext context, String[] args) {

    }
}