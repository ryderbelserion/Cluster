package com.ryderbelserion.cluster.plugin.storage.persist.objects;

import com.google.gson.annotations.Expose;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CommandData {

    @Expose
    private ArrayList<CommandCustom> subCommands = new ArrayList<>();

    public void addSubCommand(CommandCustom command) {
        this.subCommands.add(command);
    }

    public void removeSubCommand(CommandCustom command) {
        this.subCommands.remove(command);
    }

    public boolean hasSubCommand(CommandCustom command) {
        return this.subCommands.contains(command);
    }

    public void purge() {
        this.subCommands.clear();
    }

    public List<CommandCustom> getSubCommands() {
        return Collections.unmodifiableList(this.subCommands);
    }
}