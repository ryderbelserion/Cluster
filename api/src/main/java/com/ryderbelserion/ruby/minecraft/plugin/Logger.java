package com.ryderbelserion.ruby.minecraft.plugin;

import com.ryderbelserion.ruby.minecraft.RubyImpl;
import com.ryderbelserion.ruby.minecraft.plugin.registry.RubyProvider;
import org.jetbrains.annotations.NotNull;

public class Logger {

    private final @NotNull RubyImpl ruby = RubyProvider.get();

    public void info(String message) {
        send("<blue>INFO</blue> " + message);
    }

    public void warn(String message) {
        send("<gold>WARNING</gold> " + message);
    }

    public void error(String message) {
        send("<red>ERROR</red> " + message);
    }

    public void debug(String message) {
        send("<yellow>DEBUG</yellow> " + message);
    }

    private void send(String message) {
        this.ruby.audience().console().sendMessage(this.ruby.adventure().parse(message));
    }
}