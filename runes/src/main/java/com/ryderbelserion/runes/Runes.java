package com.ryderbelserion.runes;

import com.ryderbelserion.ruby.paper.PaperImpl;
import org.bukkit.plugin.java.JavaPlugin;

public class Runes extends JavaPlugin {

    public static Runes plugin;

    public PaperImpl paper;

    @Override
    public void onEnable() {
        plugin = this;

        this.paper = new PaperImpl(this);
        this.paper.enable(false);

        this.paper.getManager().setNamespace("runes");
    }

    @Override
    public void onDisable() {
        this.paper.disable();
    }
}