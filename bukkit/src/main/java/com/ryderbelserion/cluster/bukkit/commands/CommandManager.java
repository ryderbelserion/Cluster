package com.ryderbelserion.cluster.bukkit.commands;

import com.ryderbelserion.cluster.bukkit.BukkitPlugin;
import com.ryderbelserion.cluster.bukkit.registry.BukkitProvider;
import com.ryderbelserion.cluster.plugin.storage.persist.DataManager;
import com.ryderbelserion.cluster.plugin.storage.persist.objects.CustomCommand;
import org.jetbrains.annotations.NotNull;

public class CommandManager {

    private final @NotNull BukkitPlugin bukkitPlugin = BukkitProvider.get();
    private final @NotNull DataManager dataManager = this.bukkitPlugin.getDataManager();

    public void addCommand(CommandEngine command, String rootCommand, boolean root) {
        if (root) {
            this.dataManager.addCommand(command);
            return;
        }

        CustomCommand customCommand = new CustomCommand(command.getLabel());

        customCommand.setVisible(command.isVisible());

        this.dataManager.addSubCommand(command, rootCommand, customCommand);
    }

    public void removeCommand(CommandEngine command, String rootCommand, boolean root) {
        if (root) {
            this.dataManager.getCommand(rootCommand).purge();

            this.dataManager.removeCommand(rootCommand);

            this.dataManager.reload();
            return;
        }

        CustomCommand customCommand = new CustomCommand(command.getLabel());

        this.dataManager.getCommand(rootCommand).removeSubCommand(customCommand);

        this.dataManager.reload();
    }

}