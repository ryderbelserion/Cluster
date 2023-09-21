package com.ryderbelserion.cluster.bukkit.utils;

import org.bukkit.Bukkit;

public class LegacyLogger {

    private static String name = "";

    public static void setName(String prefix) {
        if (prefix.isBlank()) return;

        name = prefix;
    }

    public static void info(String message) {
        send(name + " &bINFO &r" + message);
    }

    public static void info(String message, Throwable cause) {
        info(message + ". Reason: " + cause.getMessage());
    }

    public static void warn(String message) {
        send(name + " &6WARNING &r" + message);
    }

    public static void warn(String message, Throwable cause) {
        warn(message + ". Reason: " + cause.getMessage());
    }

    public static void error(String message) {
        send(name + " &cERROR &r" + message);
    }

    public static void error(String message, Throwable cause) {
        error(message + ". Reason: " + cause.getMessage());
    }

    public static void debug(String message) {
        send(name + " &eDEBUG &r" + message);
    }

    public static void debug(String message, Throwable cause) {
        debug(message + ". Reason: " + cause.getMessage());
    }

    public static void success(String message) {
        send(name + "&aSUCCESS &r" + message);
    }

    public static void success(String message, Throwable cause) {
        success(message + ". Reason: " + cause.getMessage());
    }

    private static void send(String message) {
        Bukkit.getServer().getConsoleSender().sendMessage(LegacyUtils.color(message));
    }
}