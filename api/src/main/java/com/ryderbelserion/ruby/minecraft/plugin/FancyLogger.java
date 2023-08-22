package com.ryderbelserion.ruby.minecraft.plugin;

import com.ryderbelserion.ruby.minecraft.RubyPlugin;
import com.ryderbelserion.ruby.minecraft.plugin.registry.RubyProvider;
import org.jetbrains.annotations.NotNull;

public class FancyLogger {

    private final @NotNull RubyPlugin plugin = RubyProvider.get();

    private final String pluginName;

    public FancyLogger(String pluginName) {
        this.pluginName = pluginName;
    }

    public void info(String message) {
        send("[" + this.pluginName + "] <blue>INFO</blue> " + message);
    }

    public void warn(String message) {
        send("[" + this.pluginName + "] <gold>WARNING</gold> " + message);
    }

    public void error(String message) {
        send("[" + this.pluginName + "] <red>ERROR</red> " + message);
    }

    public void debug(String message) {
        send("[" + this.pluginName + "] <yellow>DEBUG</yellow> " + message);
    }

    private void send(String message) {
        this.plugin.getAudience().console().sendMessage(this.plugin.getAdventure().parse(message));
    }
}