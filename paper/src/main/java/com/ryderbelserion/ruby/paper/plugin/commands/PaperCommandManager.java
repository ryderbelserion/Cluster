package com.ryderbelserion.ruby.paper.plugin.commands;

import com.ryderbelserion.ruby.other.commands.args.Argument;
import com.ryderbelserion.ruby.paper.PaperPlugin;
import com.ryderbelserion.ruby.paper.plugin.registry.PaperProvider;
import org.bukkit.command.Command;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class PaperCommandManager {

    private final @NotNull PaperPlugin paperPlugin = PaperProvider.get();

    private final @NotNull JavaPlugin plugin = this.paperPlugin.getPlugin();

    private final ConcurrentHashMap<String, PaperCommandEngine> commands = new ConcurrentHashMap<>();

    private final LinkedList<PaperCommandEngine> classes = new LinkedList<>();

    private String namespace;

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public String getNamespace() {
        return this.namespace;
    }

    public void addCommand(PaperCommandEngine command, boolean first) {
        add(command, first);
    }

    public void removeCommand(PaperCommandEngine command, boolean first) {
        if (!hasCommand(command.getName())) return;

        if (first) {
            Command map = this.plugin.getServer().getCommandMap().getCommand(command.getName());

            if (map != null) map.unregister(this.plugin.getServer().getCommandMap());
        }

        if (!command.getCommands(command).isEmpty()) {
            command.getCommands(command).forEach(other -> {
                List<Argument> optional = command.getOptionalArgs(other);
                List<Argument> required = command.getRequiredArgs(other);

                if (!optional.isEmpty()) optional.clear();

                if (!required.isEmpty()) required.clear();
            });

            command.removeCommand(command, first);
        }

        if (first) this.commands.remove(command.getName());

        if (!first) this.classes.remove(command);
    }

    public boolean hasCommand(String label) {
        return this.commands.containsKey(label);
    }

    private void add(PaperCommandEngine command, boolean first) {
        if (!first) {
            if (!this.classes.contains(command)) this.classes.add(command);
        }

        if (hasCommand(command.getName())) return;

        if (!command.isVisible()) {
            if (hasCommand(command.getName())) removeCommand(command, first);
            return;
        }

        if (first) {
            this.commands.put(command.getName(), command);

            // Add it to the command map.
            this.plugin.getServer().getCommandMap().register(this.namespace, command);
        }
    }

    public Map<String, PaperCommandEngine> getCommands() {
        return Collections.unmodifiableMap(this.commands);
    }

    public LinkedList<PaperCommandEngine> getClasses() {
        return this.classes;
    }
}