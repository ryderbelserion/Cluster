package com.ryderbelserion.cluster.bukkit.commands;

import com.ryderbelserion.cluster.bukkit.BukkitPlugin;
import com.ryderbelserion.cluster.bukkit.registry.BukkitProvider;
import org.bukkit.command.Command;
import org.jetbrains.annotations.NotNull;
import java.util.List;

public abstract class CommandEngine extends Command {

    private final @NotNull BukkitPlugin bukkitPlugin = BukkitProvider.get();
    private final @NotNull CommandManager commandManager = this.bukkitPlugin.getCommandManager();

    private boolean isVisible = true;

    protected CommandEngine(@NotNull String command, @NotNull String description, @NotNull String usage, @NotNull List<String> aliases) {
        super(command, description, usage, aliases);
    }

    public abstract void perform(CommandContext context, String[] args);

    public void addSubCommand(CommandEngine command, String rootCommand) {
        this.commandManager.addCommand(command, rootCommand, false);
    }

    public void removeSubCommand(CommandEngine command, String rootCommand) {
        this.commandManager.removeCommand(command, rootCommand, false);
    }

    public void setVisible(boolean value) {
        this.isVisible = value;
    }

    public boolean isVisible() {
        return this.isVisible;
    }
}