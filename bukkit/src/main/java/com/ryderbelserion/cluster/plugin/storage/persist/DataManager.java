package com.ryderbelserion.cluster.plugin.storage.persist;

import com.ryderbelserion.cluster.api.adventure.FancyLogger;
import com.ryderbelserion.cluster.bukkit.BukkitPlugin;
import com.ryderbelserion.cluster.bukkit.commands.CommandEngine;
import com.ryderbelserion.cluster.bukkit.registry.BukkitProvider;
import com.ryderbelserion.cluster.plugin.storage.persist.objects.CustomCommand;
import org.jetbrains.annotations.NotNull;
import java.nio.file.Path;

public non-sealed class DataManager extends CommandData {

    private final @NotNull BukkitPlugin bukkitPlugin = BukkitProvider.get();

    private final Path path;

    public DataManager(Path path) {
        super(path);

        this.path = path;
    }

    public void load() {
        this.bukkitPlugin.getFileManager().addFile(new CommandData(this.path));
    }

    public void save() {
        this.bukkitPlugin.getFileManager().saveFile(new CommandData(this.path));
    }

    public void reload() {
        load();
        save();
    }

    public void addCommand(CommandEngine command) {
        if (!hasCommand(command.getLabel())) {
            commands.put(command.getLabel(), new SubCommandData());

            reload();
        }
    }

    public void addSubCommand(CommandEngine command, String rootCommand, CustomCommand customCommand) {
        if (command.getLabel().isEmpty()) {
            FancyLogger.debug("Sub command name is empty! We cannot add.");
            return;
        }

        SubCommandData data = getCommand(rootCommand);

        if (data.hasSubCommand(customCommand)) {
            FancyLogger.debug("Cannot add command! The sub command already exists.");
            return;
        }

        data.addSubCommand(customCommand);

        reload();
    }

    public void removeCommand(String command) {
        SubCommandData data = getCommand(command);

        if (!data.getSubCommands().isEmpty()) data.purge();

        commands.remove(command);

        reload();
    }

    public SubCommandData getCommand(String command) {
        if (!hasCommand(command)) return null;

        return commands.get(command);
    }

    public boolean hasCommand(String command) {
        return commands.containsKey(command);
    }
}