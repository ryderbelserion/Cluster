package com.ryderbelserion.cluster.api.adventure;

import com.ryderbelserion.cluster.api.RootPlugin;
import com.ryderbelserion.cluster.api.utils.ColorUtils;

public class FancyLogger {

    private static String name = "";

    public static void setName(String prefix) {
        if (prefix.isBlank()) return;

        name = prefix;
    }

    public static void info(String message) {
        send(name + "<blue>INFO</blue> " + message);
    }

    public static void info(String message, Throwable cause) {
        info(message + ". Reason: " + cause.getMessage());
    }

    public static void warn(String message) {
        send(name + "<gold>WARNING</gold> " + message);
    }

    public static void warn(String message, Throwable cause) {
        warn(message + ". Reason: " + cause.getMessage());
    }

    public static void error(String message) {
        send(name + "<red>ERROR</red> " + message);
    }

    public static void error(String message, Throwable cause) {
        error(message + ". Reason: " + cause.getMessage());
    }

    public static void debug(String message) {
        send(name + "<yellow>DEBUG</yellow> " + message);
    }

    public static void debug(String message, Throwable cause) {
        debug(message + ". Reason: " + cause.getMessage());
    }

    public static void success(String message) {
        send(name + "<green>SUCCESS</green> " + message);
    }

    public static void success(String message, Throwable cause) {
        success(message + ". Reason: " + cause.getMessage());
    }

    private static void send(String message) {
        RootPlugin.getConsole().sendMessage(ColorUtils.parse(message));
    }
}