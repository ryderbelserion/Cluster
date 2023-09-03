package com.ryderbelserion.cluster.plugin.storage.persist;

import com.google.gson.annotations.Expose;
import com.ryderbelserion.cluster.plugin.storage.persist.objects.CustomCommand;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SubCommandData {

    @Expose
    private ArrayList<CustomCommand> subCommands = new ArrayList<>();

    public void addSubCommand(CustomCommand command) {
        this.subCommands.add(command);
    }

    public void removeSubCommand(CustomCommand command) {
        this.subCommands.remove(command);
    }

    public boolean hasSubCommand(CustomCommand command) {
        return this.subCommands.contains(command);
    }

    public void purge() {
        this.subCommands.clear();
    }

    public List<CustomCommand> getSubCommands() {
        return Collections.unmodifiableList(this.subCommands);
    }
}