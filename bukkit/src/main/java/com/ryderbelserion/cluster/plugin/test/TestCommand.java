package com.ryderbelserion.cluster.plugin.test;

import com.ryderbelserion.cluster.bukkit.commands.CommandContext;
import com.ryderbelserion.cluster.bukkit.commands.CommandEngine;
import java.util.Collections;

public class TestCommand extends CommandEngine {

    protected TestCommand() {
        super("test", "", "", Collections.emptyList());
    }

    @Override
    public void perform(CommandContext context, String[] args) {

    }
}