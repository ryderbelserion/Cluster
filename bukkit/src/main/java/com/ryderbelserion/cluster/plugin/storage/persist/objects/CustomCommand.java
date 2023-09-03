package com.ryderbelserion.cluster.plugin.storage.persist.objects;

import com.ryderbelserion.cluster.api.commands.args.Argument;
import java.util.LinkedList;

public class CustomCommand {

    private final LinkedList<Argument> optionalArgs = new LinkedList<>();
    private final LinkedList<Argument> requiredArgs = new LinkedList<>();
    private final String name;

    private boolean isVisible = true;

    public CustomCommand(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void addOptionalArg(Argument argument) {
        if (this.optionalArgs.contains(argument)) return;

        this.optionalArgs.add(argument);
    }

    public LinkedList<Argument> getOptionalArgs() {
        return this.optionalArgs;
    }

    public void addRequiredArg(Argument argument) {
        if (this.requiredArgs.contains(argument)) return;

        this.requiredArgs.add(argument);
    }

    public LinkedList<Argument> getRequiredArgs() {
        return this.requiredArgs;
    }

    public void setVisible(boolean value) {
        this.isVisible = value;
    }

    public boolean isVisible() {
        return this.isVisible;
    }
}