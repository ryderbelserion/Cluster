package com.ryderbelserion.ruby.paper.commands;

import com.ryderbelserion.ruby.paper.Crafty;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Collections;

public class OtherCommand extends PaperCommandEngine {

    private final Crafty crafty = JavaPlugin.getPlugin(Crafty.class);

    public OtherCommand() {
        super("other", "The other command.", "crafty other", Collections.emptyList());
    }
}