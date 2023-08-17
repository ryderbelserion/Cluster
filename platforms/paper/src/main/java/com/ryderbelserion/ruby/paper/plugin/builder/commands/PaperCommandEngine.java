package com.ryderbelserion.ruby.paper.plugin.builder.commands;

import com.ryderbelserion.ruby.paper.PaperImpl;
import com.ryderbelserion.ruby.other.builder.commands.CommandEngine;
import com.ryderbelserion.ruby.paper.plugin.registry.PaperProvider;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import java.util.*;

public abstract class PaperCommandEngine extends Command implements CommandEngine {

    private final @NotNull PaperImpl paper = PaperProvider.get();

    private final @NotNull JavaPlugin plugin = this.paper.getPlugin();

    private final LinkedList<PaperCommandEngine> subs = new LinkedList<>();

    protected PaperCommandEngine(@NotNull String name, @NotNull String description, @NotNull String usageMessage, @NotNull List<String> aliases) {
        super(name, description, usageMessage, aliases);
    }

    public abstract void perform(PaperCommandContext context, String[] args);

    public void execute(PaperCommandContext context) {
        StringBuilder label = new StringBuilder(getLabel());

        if (!context.getArgs().isEmpty()) {
            for (PaperCommandEngine command : this.subs) {
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

        PaperCommandContext context = new PaperCommandContext(
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

    public void addCommand(PaperCommandEngine command) {
        if (hasCommand(command)) return;

        this.subs.add(command);
    }

    public void removeCommand(PaperCommandEngine command) {
        if (!hasCommand(command)) return;

        this.subs.remove(command);
    }

    public boolean hasCommand(PaperCommandEngine command) {
        return this.subs.contains(command);
    }
}