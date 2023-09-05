package com.ryderbelserion.cluster.api.adventure;

import com.ryderbelserion.cluster.api.RootPlugin;
import com.ryderbelserion.cluster.api.utils.ColorUtils;

public class FancyLogger {

    private static String name;

    public FancyLogger(String name) {
        FancyLogger.name = name;
    }

    public static void info(String message) {
        send(name + " <blue>INFO</blue> " + message);
    }

    public static void warn(String message) {
        send(name + " <gold>WARNING</gold> " + message);
    }

    public static void error(String message) {
        send(name + " <red>ERROR</red> " + message);
    }

    public static void debug(String message) {
        send(name + " <yellow>DEBUG</yellow> " + message);
    }

    public static void success(String message) {
        send(name + " <green>SUCCESS</green> " + message);
    }

    private static void send(String message) {
        RootPlugin.getConsole().sendMessage(ColorUtils.parse(message));
    }
}