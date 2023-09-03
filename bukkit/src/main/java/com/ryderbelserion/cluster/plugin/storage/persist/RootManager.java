package com.ryderbelserion.cluster.plugin.storage.persist;

import com.ryderbelserion.cluster.api.adventure.FancyLogger;
import com.ryderbelserion.cluster.bukkit.BukkitPlugin;
import com.ryderbelserion.cluster.bukkit.commands.CommandEngine;
import com.ryderbelserion.cluster.bukkit.registry.BukkitProvider;
import com.ryderbelserion.cluster.plugin.storage.persist.objects.CommandData;
import com.ryderbelserion.cluster.plugin.storage.persist.objects.CommandCustom;
import org.jetbrains.annotations.NotNull;
import java.nio.file.Path;

public non-sealed class RootManager extends CommandRoot {

    private final @NotNull BukkitPlugin bukkitPlugin = BukkitProvider.get();

    private final Path path;

    public RootManager(Path path) {
        super(path);

        this.path = path;
    }

    public void load() {
        this.bukkitPlugin.getFileManager().addFile(new CommandRoot(this.path));
    }

    public void save() {
        this.bukkitPlugin.getFileManager().saveFile(new CommandRoot(this.path));
    }

    public void reload() {
        load();
        save();
    }

    public void addCommand(CommandEngine command) {
        if (!hasCommand(command.getLabel())) {
            commands.put(command.getLabel(), new CommandData());

            reload();
        }
    }

    public void addSubCommand(CommandEngine command, String rootCommand, CommandCustom commandCustom) {
        if (command.getLabel().isEmpty()) {
            FancyLogger.debug("Sub command name is empty! We cannot add.");
            return;
        }

        CommandData data = getCommand(rootCommand);

        if (data.hasSubCommand(commandCustom)) {
            FancyLogger.debug("Cannot add command! The sub command already exists.");
            return;
        }

        data.addSubCommand(commandCustom);

        reload();
    }

    public void removeCommand(String command) {
        CommandData data = getCommand(command);

        if (!data.getSubCommands().isEmpty()) data.purge();

        commands.remove(command);

        reload();
    }

    public CommandData getCommand(String command) {
        if (!hasCommand(command)) return null;

        return commands.get(command);
    }

    public boolean hasCommand(String command) {
        return commands.containsKey(command);
    }
}