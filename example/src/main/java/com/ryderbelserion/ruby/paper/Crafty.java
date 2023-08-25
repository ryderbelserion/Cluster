package com.ryderbelserion.ruby.paper;

import com.ryderbelserion.ruby.paper.commands.BaseCommand;
import com.ryderbelserion.ruby.paper.commands.OtherCommand;
import org.bukkit.plugin.java.JavaPlugin;

public class Crafty extends JavaPlugin {

    private PaperPlugin ruby;

    @Override
    public void onEnable() {
        // This must go first!
        this.ruby = new PaperPlugin(this);
        this.ruby.enable(false);

        this.ruby.getCommandManager().addCommand(new BaseCommand(), "crafty", true);
        this.ruby.getCommandManager().addCommand(new OtherCommand(), "crafty", false);

        this.ruby.getFancyLogger().debug("Guten Tag!");
    }

    @Override
    public void onDisable() {
        this.ruby.getFancyLogger().debug("Gute Nacht!");

        // This must go last!
        this.ruby.disable();
    }
}