package com.ryderbelserion.cluster.bukkit.commands;

import com.ryderbelserion.cluster.bukkit.BukkitPlugin;
import com.ryderbelserion.cluster.bukkit.registry.BukkitProvider;
import com.ryderbelserion.cluster.plugin.storage.persist.RootManager;
import com.ryderbelserion.cluster.plugin.storage.persist.objects.CommandCustom;
import com.ryderbelserion.cluster.plugin.storage.persist.objects.CommandData;
import org.jetbrains.annotations.NotNull;

public class CommandManager {

    private final @NotNull BukkitPlugin bukkitPlugin = BukkitProvider.get();
    private final @NotNull RootManager dataManager = this.bukkitPlugin.getDataManager();

    public void addCommand(CommandEngine command, String rootCommand, boolean root) {
        if (root) {
            this.dataManager.addCommand(command);
            return;
        }

        CommandCustom commandCustom = new CommandCustom(command.getLabel());

        commandCustom.setVisible(command.isVisible());

        this.dataManager.addSubCommand(command, rootCommand, commandCustom);
    }

    public void removeCommand(CommandEngine command, String rootCommand, boolean root) {
        if (root) {
            this.dataManager.getCommand(rootCommand).purge();

            this.dataManager.removeCommand(rootCommand);

            this.dataManager.reload();
            return;
        }

        CommandCustom commandCustom = new CommandCustom(command.getLabel());

        this.dataManager.getCommand(rootCommand).removeSubCommand(commandCustom);

        this.dataManager.reload();
    }

    public CommandData getCommand(String rootCommand) {
        return this.dataManager.getCommand(rootCommand);
    }

    public CommandCustom getSubCommand(CommandEngine command, String rootCommand) {
        CommandCustom commandCustom = new CommandCustom(command.getLabel());

        for (CommandCustom value : getCommand(rootCommand).getSubCommands()) {
            if (value.getName().equals(commandCustom.getName())) return commandCustom;
        }

        return null;
    }
}