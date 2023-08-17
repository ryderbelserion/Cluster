package com.ryderbelserion.ruby.folia.plugin.builder.commands;

import com.ryderbelserion.ruby.folia.FoliaImpl;
import com.ryderbelserion.ruby.paper.plugin.registry.FoliaProvider;
import org.bukkit.command.Command;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public class FoliaCommandManager {

    private final @NotNull FoliaImpl paper = FoliaProvider.get();

    private final @NotNull JavaPlugin plugin = this.paper.getPlugin();

    private String namespace;

    public FoliaCommandManager setNamespace(String namespace) {
        this.namespace = namespace;

        return this;
    }

    public void addCommand(Command command) {
        this.plugin.getServer().getCommandMap().register(this.namespace, command);
    }

    public String getNamespace() {
        return this.namespace;
    }
}