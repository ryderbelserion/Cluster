package com.ryderbelserion.ruby.paper.commands;

import com.ryderbelserion.ruby.paper.Crafty;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.Collections;

public class BaseCommand extends PaperCommandEngine {

    private final Crafty crafty = JavaPlugin.getPlugin(Crafty.class);

    public BaseCommand() {
        super("crafty", "The base crafty command.", "crafty", Collections.emptyList());
    }
}