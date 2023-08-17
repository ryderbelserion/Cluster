package com.ryderbelserion.ruby.paper.plugin.builder.commands;

import com.ryderbelserion.ruby.paper.PaperImpl;
import com.ryderbelserion.ruby.paper.plugin.registry.PaperProvider;
import org.bukkit.command.Command;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public class PaperCommandManager {

    private final @NotNull PaperImpl paper = PaperProvider.get();

    private final @NotNull JavaPlugin plugin = this.paper.getPlugin();

    private String namespace;

    public PaperCommandManager setNamespace(String namespace) {
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