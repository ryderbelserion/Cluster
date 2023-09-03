package com.ryderbelserion.cluster.api.commands;

import com.ryderbelserion.cluster.api.utils.ColorUtils;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import java.util.ArrayList;
import java.util.List;

public class ExtraContext {

    private final ArrayList<String> args;
    private final Audience audience;
    private String label;

    public ExtraContext(Audience audience, String label, List<String> args) {
        this.audience = audience;
        this.label = label;

        this.args = new ArrayList<>();

        this.args.addAll(args);
    }

    public void reply(String message) {
        if (empty(message)) return;

        Component component = ColorUtils.parse(message);

        this.audience.sendMessage(component);
    }

    public void reply(Component message) {
        if (message == null) return;

        this.audience.sendMessage(message);
    }

    public void send(Component component) {
        if (component == null) return;

        this.audience.sendMessage(component);
    }

    public void send(Audience audience, String message) {
        if (empty(message)) return;

        Component component = ColorUtils.parse(message);

        audience.sendMessage(component);
    }

    public void send(Audience audience, Component component) {
        if (component == null) return;

        audience.sendMessage(component);
    }

    private boolean empty(String message) {
        return message.isBlank() || message.isEmpty();
    }

    public Audience getSender() {
        return this.audience;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getLabel() {
        return this.label;
    }

    public ArrayList<String> getArgs() {
        return this.args;
    }
}