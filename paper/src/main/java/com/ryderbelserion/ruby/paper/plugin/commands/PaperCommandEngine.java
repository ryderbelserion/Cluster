package com.ryderbelserion.ruby.paper.plugin.commands;

import com.ryderbelserion.ruby.other.builder.ComponentBuilder;
import com.ryderbelserion.ruby.other.commands.CommandEngine;
import com.ryderbelserion.ruby.other.commands.CommandHelpProvider;
import com.ryderbelserion.ruby.other.commands.args.Argument;
import com.ryderbelserion.ruby.paper.PaperPlugin;
import com.ryderbelserion.ruby.paper.plugin.commands.reqs.PaperRequirements;
import com.ryderbelserion.ruby.paper.plugin.registry.PaperProvider;
import net.kyori.adventure.text.event.ClickEvent;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import java.util.*;

public abstract class PaperCommandEngine extends Command implements CommandEngine {

    private final @NotNull PaperPlugin plugin = PaperProvider.get();
    private final @NotNull CommandHelpProvider locale = this.plugin.getHelpProvider();

    public PaperRequirements paperRequirements;

    private final LinkedList<PaperCommandEngine> subCommands = new LinkedList<>();

    private final LinkedList<Argument> requiredArgs = new LinkedList<>();
    private final LinkedList<Argument> optionalArgs = new LinkedList<>();

    public PaperCommandEngine(@NotNull String name, @NotNull String description, @NotNull String usageMessage, @NotNull List<String> aliases) {
        super(name, description, usageMessage, aliases);
    }

    public abstract void perform(PaperCommandContext context, String[] args);

    public void execute(PaperCommandContext context, String[] args) {
        StringBuilder label = new StringBuilder(context.getLabel());

        if (!context.getArgs().isEmpty()) {
            for (PaperCommandEngine command : this.subCommands) {
                boolean isPresent = context.getArgs().stream().findFirst().isPresent();

                String arg = context.getArgs().stream().findFirst().get().toLowerCase();

                if (isPresent && arg.equalsIgnoreCase(command.getLabel())) {
                    label.append(" ").append(context.getArgs().get(0));

                    context.getArgs().remove(0);
                    context.setLabel(label.toString());

                    if (!validate(context, command)) return;

                    command.execute(context, args);

                    return;
                }
            }
        }

        if (paperRequirements != null) {
            if (!paperRequirements.checkRequirements(context, true)) return;
        }

        perform(context, args);
    }

    private boolean validate(PaperCommandContext context, PaperCommandEngine command) {
        if (context.getArgs().size() < command.requiredArgs.size()) {
            context.reply(this.locale.notEnoughArgs());
            format(context, command);
            return false;
        }

        if (context.getArgs().size() > command.requiredArgs.size() + command.optionalArgs.size() || context.getArgs().size() > command.requiredArgs.size()) {
            context.reply(this.locale.tooManyArgs());
            format(context, command);
            return false;
        }

        return true;
    }

    private void format(PaperCommandContext context, PaperCommandEngine command) {
        ArrayList<Argument> arguments = new ArrayList<>();

        arguments.addAll(command.requiredArgs);
        arguments.addAll(command.optionalArgs);

        command.requiredArgs.sort(Comparator.comparing(Argument::order));

        if (context.isPlayer()) {
            StringBuilder baseFormat = new StringBuilder("/" + command.getUsage());

            String format = this.locale.invalidFormat().replaceAll("\\{command}", baseFormat.toString());

            StringBuilder types = new StringBuilder();

            ComponentBuilder builder = new ComponentBuilder();

            for (Argument arg : arguments) {
                boolean isPresent = arg.argumentType().getPossibleValues().stream().findFirst().isPresent();

                if (isPresent) types.append(arg.argumentType().getPossibleValues().stream().findFirst().get());
            }

            builder.setMessage(format.replaceAll("\\{args}", String.valueOf(types)));

            String hoverShit = baseFormat.append(" ").append(types).toString();

            String hoverFormat = this.locale.hoverMessage();

            builder.hover(hoverFormat.replaceAll("\\{command}", hoverShit)).click(ClickEvent.Action.SUGGEST_COMMAND, hoverShit);

            context.reply(builder.build());

            return;
        }

        StringBuilder baseFormat = new StringBuilder("/" + command.getUsage());

        StringBuilder types = new StringBuilder();

        for (Argument arg : arguments) {
            boolean isPresent = arg.argumentType().getPossibleValues().stream().findFirst().isPresent();

            if (isPresent) types.append(arg.argumentType().getPossibleValues().stream().findFirst().get());
        }

        context.reply(baseFormat.append(types).toString());
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String label, @NotNull String[] args) {
        PaperCommandContext context = new PaperCommandContext(
                sender,
                label,
                List.of(args)
        );

        execute(context, args);

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
                        if (!argument.argumentType().getPossibleValues().isEmpty()) {
                            List<String> possibleArgs = argument.argumentType().getPossibleValues();

                            possibleValues = new ArrayList<>(possibleArgs);
                            break;
                        }
                    }
                }

                return Collections.unmodifiableList(possibleValues);
            }
        }

        return Collections.emptyList();
    }

    public void addCommand(PaperCommandEngine command, boolean first) {
        if (hasCommand(command)) return;

        this.plugin.getManager().addCommand(command, first);

        this.subCommands.add(command);
    }

    public void removeCommand(PaperCommandEngine command, boolean first) {
        if (!hasCommand(command)) return;

        this.plugin.getManager().removeCommand(command, first);

        this.subCommands.remove(command);
    }

    public boolean hasCommand(PaperCommandEngine command) {
        return this.subCommands.contains(command);
    }

    public List<PaperCommandEngine> getCommands(PaperCommandEngine command) {
        return Collections.unmodifiableList(command.subCommands);
    }

    public List<Argument> getOptionalArgs(PaperCommandEngine command) {
        return command.requiredArgs;
    }

    public List<Argument> getRequiredArgs(PaperCommandEngine command) {
        return command.optionalArgs;
    }

    public List<Argument> getOptionalArgs() {
        return this.requiredArgs;
    }

    public List<Argument> getRequiredArgs() {
        return this.optionalArgs;
    }

    public void addRequiredArgument(PaperCommandEngine command, Argument argument) {
        command.requiredArgs.add(argument);
    }

    public void addOptionalArgument(PaperCommandEngine command, Argument argument) {
        command.optionalArgs.add(argument);
    }
}