package com.ryderbelserion.ruby.paper.plugin.builder.commands;

import com.ryderbelserion.ruby.other.builder.commands.args.Argument;
import com.ryderbelserion.ruby.paper.PaperImpl;
import com.ryderbelserion.ruby.other.builder.commands.CommandEngine;
import com.ryderbelserion.ruby.paper.plugin.registry.PaperProvider;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Array;
import java.util.*;

public abstract class PaperCommandEngine extends Command implements CommandEngine {

    private final @NotNull PaperImpl paper = PaperProvider.get();

    private final @NotNull JavaPlugin plugin = this.paper.getPlugin();

    private final LinkedList<PaperCommandEngine> subCommands = new LinkedList<>();

    public LinkedList<Argument> requiredArgs = new LinkedList<>();
    public LinkedList<Argument> optionalArgs = new LinkedList<>();

    protected PaperCommandEngine(@NotNull String name, @NotNull String description, @NotNull String usageMessage, @NotNull List<String> aliases) {
        super(name, description, usageMessage, aliases);
    }

    public abstract void perform(PaperCommandContext context, String[] args);

    public void execute(PaperCommandContext context) {
        StringBuilder label = new StringBuilder(getLabel());

        if (!context.getArgs().isEmpty()) {
            for (PaperCommandEngine command : this.subCommands) {
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

        //if (!validation(context)) return;

        perform(context, new String[0]);
    }

    private boolean validation(PaperCommandContext context) {
        if (context.getArgs().size() < this.requiredArgs.size()) {
            context.reply("You are a few hairs short.");
            //TODO() Add format
            return false;
        }

        if (context.getArgs().size() > this.requiredArgs.size() + this.optionalArgs.size()
        || context.getArgs().size() > this.requiredArgs.size()) {
            context.reply("You are a spicy beaver.");
            //TODO() Add format
            return false;
        }

        return true;
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

        return true;
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
                this.subCommands.forEach(sub -> {
                    if (!sub.isVisible()) return;

                    completions.add(sub.getName());
                });

                return completions;
            }

            return completions;
        }

        if (args.size() >= 2) {
            int relativeIndex = 2;
            int subCommandIndex = 0;

            PaperCommandEngine commandTab = this;

            while (!commandTab.subCommands.isEmpty()) {
                PaperCommandEngine findCommand = null;

                for (PaperCommandEngine sub : this.subCommands) {
                    if (sub.getName().equals(args.get(subCommandIndex).toLowerCase())) {
                        findCommand = sub;
                        break;
                    }
                }

                subCommandIndex++;
                if (findCommand != null) commandTab = findCommand; else break;
                relativeIndex++;
            }

            int argToComplete = args.size() + 1 - relativeIndex;

            if (commandTab.requiredArgs.size() >= argToComplete) {
                ArrayList<Argument> arguments = new ArrayList<>();

                arguments.addAll(commandTab.requiredArgs);
                arguments.addAll(commandTab.optionalArgs);

                ArrayList<String> possibleValues = new ArrayList<>();

                for (Argument argument : arguments) {
                    if (argument.order() == argToComplete) {
                        List<String> possibleValuesArgs = argument.argumentType().getPossibleValues();

                        possibleValues = new ArrayList<>(possibleValuesArgs);
                        break;
                    }
                }

                return Collections.unmodifiableList(possibleValues);
            }
        }

        return Collections.emptyList();
    }

    public void addCommand(PaperCommandEngine command) {
        if (hasCommand(command)) return;

        this.subCommands.add(command);
    }

    public void removeCommand(PaperCommandEngine command) {
        if (!hasCommand(command)) return;

        this.subCommands.remove(command);
    }

    public boolean hasCommand(PaperCommandEngine command) {
        return this.subCommands.contains(command);
    }
}