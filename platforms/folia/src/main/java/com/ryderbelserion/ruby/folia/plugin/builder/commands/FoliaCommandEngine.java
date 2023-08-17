package com.ryderbelserion.ruby.folia.plugin.builder.commands;

import com.ryderbelserion.ruby.folia.FoliaImpl;
import com.ryderbelserion.ruby.other.builder.commands.CommandEngine;
import com.ryderbelserion.ruby.paper.plugin.registry.FoliaProvider;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import java.util.*;

public abstract class FoliaCommandEngine extends Command implements CommandEngine {

    private final @NotNull FoliaImpl paper = FoliaProvider.get();

    private final @NotNull JavaPlugin plugin = this.paper.getPlugin();

    private final LinkedList<FoliaCommandEngine> subs = new LinkedList<>();

    protected FoliaCommandEngine(@NotNull String name, @NotNull String description, @NotNull String usageMessage, @NotNull List<String> aliases) {
        super(name, description, usageMessage, aliases);
    }

    public abstract void perform(FoliaCommandContext context, String[] args);

    public void execute(FoliaCommandContext context) {
        StringBuilder label = new StringBuilder(getLabel());

        if (!context.getArgs().isEmpty()) {
            for (FoliaCommandEngine command : this.subs) {
                boolean isPresent = context.getArgs().stream().findFirst().isPresent();

                String arg = context.getArgs().stream().findFirst().get().toLowerCase();

                if (isPresent && arg.equalsIgnoreCase(command.getLabel())) {
                    label.append(" ").append(context.getArgs().get(0));

                    context.setLabel(label.toString());

                    command.execute(context);

                    return;
                }
            }
        }

        perform(context, new String[0]);
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String label, @NotNull String[] args) {
        List<String> arguments = Arrays.asList(args);

        FoliaCommandContext context = new FoliaCommandContext(
                sender,
                label,
                arguments
        );

        execute(context);

        return false;
    }

    private boolean value = true;

    @Override
    public void setVisible(boolean value) {
        this.value = value;
    }

    @Override
    public boolean isVisible() {
        return this.value;
    }

    public List<String> handleTabComplete(List<String> args) {
        if (args.size() == 1) {
            ArrayList<String> completions = new ArrayList<>();

            if (args.get(0).isEmpty()) {
                this.subs.forEach(sub -> {
                    if (!sub.isVisible()) return;

                    completions.add(sub.getName());
                });

                return completions;
            }

            return completions;
        }

        return Collections.emptyList();
    }

    public void addCommand(FoliaCommandEngine command) {
        if (hasCommand(command)) return;

        this.subs.add(command);
    }

    public void removeCommand(FoliaCommandEngine command) {
        if (!hasCommand(command)) return;

        this.subs.remove(command);
    }

    public boolean hasCommand(FoliaCommandEngine command) {
        return this.subs.contains(command);
    }
}