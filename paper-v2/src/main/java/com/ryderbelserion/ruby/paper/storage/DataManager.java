package com.ryderbelserion.ruby.paper.storage;

import com.ryderbelserion.ruby.minecraft.FancyLogger;
import com.ryderbelserion.ruby.paper.PaperPlugin;
import com.ryderbelserion.ruby.paper.registry.PaperProvider;
import com.ryderbelserion.ruby.paper.storage.objects.CustomCommand;
import com.ryderbelserion.ruby.paper.storage.objects.SubCommandData;
import org.jetbrains.annotations.NotNull;
import java.nio.file.Path;

public non-sealed class DataManager extends CommandData {

    private final @NotNull PaperPlugin paperPlugin = PaperProvider.get();
    private final @NotNull FancyLogger fancyLogger = this.paperPlugin.getFancyLogger();

    public DataManager(Path path) {
        super(path);
    }

    public void load() {

    }

    public void save() {

    }

    public void reload() {
        save();
        load();
    }

    public void addCommand(String command, String subCommand) {
        if (!hasCommand(command)) {
            commands.put(command, new SubCommandData());

            reload();

            return;
        }

        SubCommandData data = getCommand(command);

        CustomCommand customCommand = new CustomCommand(subCommand);

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