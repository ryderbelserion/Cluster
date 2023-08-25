package com.ryderbelserion.ruby.paper.storage;

import com.ryderbelserion.ruby.adventure.FancyLogger;
import com.ryderbelserion.ruby.paper.PaperPlugin;
import com.ryderbelserion.ruby.paper.commands.PaperCommandEngine;
import com.ryderbelserion.ruby.paper.registry.PaperProvider;
import com.ryderbelserion.ruby.paper.storage.objects.CustomCommand;
import com.ryderbelserion.ruby.paper.storage.objects.SubCommandData;
import org.jetbrains.annotations.NotNull;
import java.nio.file.Path;

public non-sealed class DataManager extends CommandData {

    private final @NotNull PaperPlugin paperPlugin = PaperProvider.get();
    private final @NotNull FancyLogger fancyLogger = this.paperPlugin.getFancyLogger();

    private final Path path;

    public DataManager(Path path) {
        super(path);

        this.path = path;
    }

    public void load() {
        this.paperPlugin.getFileManager().addFile(new CommandData(this.path));
    }

    public void save() {
        this.paperPlugin.getFileManager().saveFile(new CommandData(this.path));
    }

    public void reload() {
        load();
        save();
    }

    public void addCommand(PaperCommandEngine command) {
        if (!hasCommand(command.getLabel())) {
            commands.put(command.getLabel(), new SubCommandData());

            reload();
        }
    }

    public void addSubCommand(PaperCommandEngine command, String rootCommand, CustomCommand customCommand) {
        if (command.getLabel().isEmpty()) {
            this.fancyLogger.debug("Sub command name is empty! We cannot add.");
            return;
        }

        SubCommandData data = getCommand(rootCommand);

        if (data.hasSubCommand(customCommand)) {
            this.fancyLogger.debug("Cannot add command! The sub command already exists.");
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