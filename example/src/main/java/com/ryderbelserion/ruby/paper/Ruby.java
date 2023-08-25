package com.ryderbelserion.ruby.paper;

import com.ryderbelserion.ruby.paper.commands.CraftyCommand;
import com.ryderbelserion.ruby.paper.commands.CraftyHelpData;
import com.ryderbelserion.ruby.paper.plugin.commands.PaperCommandManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Ruby extends JavaPlugin {

    private PaperPlugin paperPlugin;

    @Override
    public void onEnable() {
        // This must go first!
        this.paperPlugin = new PaperPlugin(this);
        this.paperPlugin.enable(false);

        this.paperPlugin.setHelpProvider(new CraftyHelpData());

        PaperCommandManager manager = this.paperPlugin.getManager();

        manager.setNamespace("crafty");

        manager.addCommand(new CraftyCommand(), true);

        this.paperPlugin.getFancyLogger().debug("Guten Tag!");
    }

    @Override
    public void onDisable() {
        this.paperPlugin.getFancyLogger().debug("Gute Nacht!");

        // This must go last!
        this.paperPlugin.disable();
    }
}