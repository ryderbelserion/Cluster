package com.ryderbelserion.runes.cmd;

import com.ryderbelserion.ruby.paper.plugin.builder.commands.PaperCommandContext;
import com.ryderbelserion.ruby.paper.plugin.builder.commands.PaperCommandEngine;
import java.util.Collections;

public class BaseCommand extends PaperCommandEngine {

    public BaseCommand() {
        super("players", "A runes command to handle players", "crazyrunes:players", Collections.emptyList());
    }

    @Override
    public void perform(PaperCommandContext context, String[] args) {

    }
}