package com.ryderbelserion.ruby.minecraft.plugin;

import com.ryderbelserion.ruby.minecraft.RubyImpl;
import com.ryderbelserion.ruby.other.registry.RubyProvider;
import org.jetbrains.annotations.NotNull;

public class Logger {

    private final @NotNull RubyImpl ruby = RubyProvider.get();

    private final String pluginName;

    public Logger(String pluginName) {
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
        this.ruby.audience().console().sendMessage(this.ruby.adventure().parse(message));
    }
}