package com.ryderbelserion.runes;

import com.ryderbelserion.ruby.paper.PaperImpl;
import com.ryderbelserion.runes.commands.subs.RunesBaseCommand;
import com.ryderbelserion.runes.commands.subs.testing.ExampleCommand;
import com.ryderbelserion.runes.commands.subs.testing.ExampleCommandTwo;
import org.bukkit.plugin.java.JavaPlugin;

public class Runes extends JavaPlugin {

    public static Runes plugin;

    public PaperImpl paper;

    public ExampleCommand exampleCommand;
    public ExampleCommandTwo exampleCommandTwo;

    @Override
    public void onEnable() {
        plugin = this;

        this.paper = new PaperImpl(this);
        this.paper.enable(false);

        this.paper.getManager().setNamespace("runes");

        this.paper.getManager().addCommand(new RunesBaseCommand());

        this.paper.getManager().addCommand(this.exampleCommand = new ExampleCommand());
        this.paper.getManager().addCommand(this.exampleCommandTwo = new ExampleCommandTwo());
    }

    @Override
    public void onDisable() {
        this.paper.disable();
    }
}