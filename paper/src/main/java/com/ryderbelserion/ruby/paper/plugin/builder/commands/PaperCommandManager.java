package com.ryderbelserion.ruby.paper.plugin.builder.commands;

import com.ryderbelserion.ruby.paper.PaperImpl;
import com.ryderbelserion.ruby.paper.plugin.registry.PaperProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class PaperCommandManager {

    private final @NotNull PaperImpl paper = PaperProvider.get();

    private final @NotNull JavaPlugin plugin = this.paper.getPlugin();

    private final ConcurrentHashMap<String, PaperCommandEngine> commands = new ConcurrentHashMap<>();

    private String namespace;

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public String getNamespace() {
        return this.namespace;
    }

    public void addCommand(PaperCommandEngine command) {
        add(command);
    }

    public void removeCommand(PaperCommandEngine command) {

    }

    public boolean hasCommand(String label) {
        return this.commands.containsKey(label);
    }

    private void add(PaperCommandEngine command) {
        if (hasCommand(command.getName())) {
            this.paper.getLogger().info("Label: " + command.getLabel());
            this.paper.getLogger().info("Name: " + command.getName());

            return;
        }

        if (!command.isVisible()) {
            this.paper.getLogger().info("Label: " + command.getLabel());
            this.paper.getLogger().info("Name: " + command.getName());

            this.paper.getLogger().info("Visibility Status: " + command.isVisible());

            if (hasCommand(command.getName())) {
                this.paper.getLogger().info("Command exists so we must remove it.");
                removeCommand(command);
            }

            return;
        }

        this.commands.put(command.getName(), command);

        this.paper.getLogger().info("Command Map Size: " + this.commands.size());

        this.paper.getLogger().info("Command Name: " + command.getName() + " : " + command.getLabel());

        // Add it to the command map.
        this.plugin.getServer().getCommandMap().register(this.namespace, command);
    }

    public Map<String, PaperCommandEngine> getCommands() {
        return Collections.unmodifiableMap(this.commands);
    }
}