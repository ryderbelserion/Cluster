package com.ryderbelserion.ruby.paper.commands;

import com.ryderbelserion.ruby.paper.PaperPlugin;
import com.ryderbelserion.ruby.paper.registry.PaperProvider;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import java.util.List;

public class PaperCommandEngine extends Command {

    private final @NotNull PaperPlugin plugin = PaperProvider.get();
    private final PaperCommandManager commandManager = this.plugin.getCommandManager();

    private boolean isVisible = true;

    protected PaperCommandEngine(@NotNull String command, @NotNull String description, @NotNull String usage, @NotNull List<String> aliases) {
        super(command, description, usage, aliases);
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        return false;
    }

    public void addSubCommand(PaperCommandEngine command, String rootCommand) {
        this.commandManager.addCommand(command, rootCommand, false);
    }

    public void removeSubCommand(PaperCommandEngine command, String rootCommand) {
        this.commandManager.removeCommand(command, rootCommand, false);
    }

    public void setVisible(boolean value) {
        this.isVisible = value;
    }

    public boolean isVisible() {
        return this.isVisible;
    }
}