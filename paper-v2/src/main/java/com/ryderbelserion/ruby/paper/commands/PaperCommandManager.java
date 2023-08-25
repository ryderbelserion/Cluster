package com.ryderbelserion.ruby.paper.commands;

import com.ryderbelserion.ruby.paper.PaperPlugin;
import com.ryderbelserion.ruby.paper.registry.PaperProvider;
import com.ryderbelserion.ruby.paper.storage.DataManager;
import com.ryderbelserion.ruby.paper.storage.objects.CustomCommand;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public class PaperCommandManager {

    private final @NotNull PaperPlugin paperPlugin = PaperProvider.get();
    private final @NotNull DataManager dataManager = this.paperPlugin.getDataManager();

    private final @NotNull JavaPlugin plugin = this.paperPlugin.getPlugin();

    public void addCommand(PaperCommandEngine command, String rootCommand, boolean root) {
        if (root) {
            this.dataManager.addCommand(command);
            return;
        }

        CustomCommand customCommand = new CustomCommand(command.getLabel());

        customCommand.setVisible(command.isVisible());

        this.dataManager.addSubCommand(command, rootCommand, customCommand);
    }

    public void removeCommand(PaperCommandEngine command, String rootCommand, boolean root) {
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