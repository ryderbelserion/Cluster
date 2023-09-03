package com.ryderbelserion.cluster.bukkit.commands;

import com.ryderbelserion.cluster.bukkit.BukkitPlugin;
import com.ryderbelserion.cluster.bukkit.registry.BukkitProvider;
import com.ryderbelserion.cluster.plugin.storage.persist.objects.CommandCustom;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import java.util.List;

public abstract class CommandEngine extends Command {

    private final @NotNull BukkitPlugin bukkitPlugin = BukkitProvider.get();
    private final @NotNull CommandManager commandManager = this.bukkitPlugin.getCommandManager();

    private boolean isVisible = true;

    private String rootCommand;

    protected CommandEngine(@NotNull String command, @NotNull String description, @NotNull String usage, @NotNull List<String> aliases) {
        super(command, description, usage, aliases);
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        return false;
    }

    public abstract void perform(CommandContext context, String[] args);

    public void execute(CommandContext context, String[] args) {
        StringBuilder label = new StringBuilder(context.getLabel());

        if (!context.getArgs().isEmpty()) {
            for (CommandCustom command : this.commandManager.getCommand(this.rootCommand).getSubCommands()) {
                boolean isPresent = context.getArgs().stream().findFirst().isPresent();

                String arg = context.getArgs().stream().findFirst().get().toLowerCase();

                if (isPresent && arg.equalsIgnoreCase(command.getName())) {
                    label.append(" ").append(context.getArgs().get(0));

                    context.getArgs().remove(0);
                    context.setLabel(label.toString());

                    //command.execute(context, args);

                    return;
                }
            }
        }

        perform(context, args);
    }

    public void addSubCommand(CommandEngine command) {
        this.commandManager.addCommand(command, this.rootCommand, false);
    }

    public void removeSubCommand(CommandEngine command) {
        this.commandManager.removeCommand(command, this.rootCommand, false);
    }

    public void setVisible(boolean value) {
        this.isVisible = value;
    }

    public void setRootCommand(String rootCommand) {
        this.rootCommand = rootCommand;
    }

    public boolean isVisible() {
        return this.isVisible;
    }

    public String getRootCommand() {
        return this.rootCommand;
    }
}