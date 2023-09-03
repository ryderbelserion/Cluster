package com.ryderbelserion.cluster.bukkit.commands;

import com.ryderbelserion.cluster.bukkit.BukkitPlugin;
import com.ryderbelserion.cluster.bukkit.registry.BukkitProvider;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import java.util.List;

public class CommandEngine extends Command {

    private final @NotNull BukkitPlugin plugin = BukkitProvider.get();
    private final @NotNull CommandManager commandManager = this.plugin.getCommandManager();

    private boolean isVisible = true;

    protected CommandEngine(@NotNull String command, @NotNull String description, @NotNull String usage, @NotNull List<String> aliases) {
        super(command, description, usage, aliases);
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        return false;
    }

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