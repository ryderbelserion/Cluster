package com.ryderbelserion.ruby.paper.plugin.commands;

import com.ryderbelserion.ruby.other.builder.ComponentBuilder;
import com.ryderbelserion.ruby.other.commands.CommandHelpProvider;
import com.ryderbelserion.ruby.other.commands.args.Argument;
import com.ryderbelserion.ruby.paper.PaperPlugin;
import com.ryderbelserion.ruby.paper.plugin.registry.PaperProvider;
import net.kyori.adventure.text.event.ClickEvent;
import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;
import java.util.Comparator;

public class PaperCommandHelpEntry {

    //TODO() Finish setting up the navigation using the proper component builder.
    //TODO() Figure out why there is a blank space when copying the command.
    //TODO() Hide commands if they don't have the permission to use them.
    //TODO() Add the (page) or (player) args to the help menu potentially? It would get funky after 5+ args though.

    private final @NotNull PaperPlugin plugin = PaperProvider.get();

    private final @NotNull PaperCommandManager manager = this.plugin.getManager();

    private final @NotNull CommandHelpProvider locale = this.plugin.getHelpProvider();

    private int page = 1;
    private int perPage;
    private int totalPages;
    private int totalResults;
    private boolean lastPage;

    public PaperCommandHelpEntry() {
        this.perPage = this.plugin.getHelpProvider().defaultHelpPerPage();
    }

    public void showHelp(PaperCommandContext context) {
        int min = this.perPage * (this.page - 1);
        int max = min + this.perPage;

        this.totalResults = this.manager.getClasses().size();

        this.totalPages = this.totalResults / this.perPage;

        if (min >= this.totalResults) {
            context.reply(this.locale.invalidPage().replaceAll("\\{page}", String.valueOf(page)));
            return;
        }

        context.reply(this.locale.pageHeader().replaceAll("\\{page}", String.valueOf(page)));

        for (int value = min; value < max; value++) {
            if (this.totalResults - 1 < value) continue;

            PaperCommandEngine command = this.manager.getClasses().get(value);

            boolean isVisible = command.isVisible();

            if (!isVisible) return;

            StringBuilder baseFormat = new StringBuilder("/" + command.getUsage());

            String format = this.locale.pageFormat()
                    .replaceAll("\\{command}", baseFormat.toString())
                    .replaceAll("\\{description}", command.getDescription());

            if (!command.getAliases().isEmpty()) baseFormat.append(" ").append(command.getAliases().get(0));

            ArrayList<Argument> arguments = new ArrayList<>();

            arguments.addAll(command.getOptionalArgs());
            arguments.addAll(command.getRequiredArgs());

            arguments.sort(Comparator.comparingInt(Argument::order));

            if (context.isPlayer()) {
                StringBuilder types = new StringBuilder();

                ComponentBuilder builder = new ComponentBuilder();

                for (Argument arg : arguments) {
                    String argValue = command.getOptionalArgs().contains(arg) ? " (" + arg.name() + ") " : " <" + arg.name() + ">";

                    types.append(argValue);
                }

                builder.setMessage(format.replaceAll("\\{args}", String.valueOf(types)));

                String hoverShit = baseFormat.append(types).toString();

                String hoverFormat = this.locale.hoverMessage();

                builder.hover(hoverFormat.replaceAll("\\{command}", hoverShit)).click(ClickEvent.Action.valueOf(this.locale.hoverAction().toUpperCase()), hoverShit);

                context.reply(builder.build());
            }

            String footer = this.locale.pageFooter();

            if (context.isPlayer()) {
                String text = this.locale.pageNavigation();

                ComponentBuilder builder = new ComponentBuilder();

                if (page > 1) {
                    int number = page-1;

                    String fullUsage = "/" + command.getUsage() + " " + number;
                    String newNumber = String.valueOf(number);

                    builder.setMessage(footer.replaceAll("\\{page}", String.valueOf(page)));

                    builder.hover(text.replaceAll("\\{page}", String.valueOf(number)));

                    context.reply(builder.build());

                    //this.plugin.getAdventure().hover(context.getPlayer(), footer.replaceAll("\\{page}", newPage), text.replaceAll("\\{page}", newNumber), this.locale.pageBackButton(), fullUsage, ClickEvent.Action.RUN_COMMAND);
                } else if (page < this.manager.getClasses().size()) {
                    //int number = page+1;

                    //String fullUsage = "/" + command.getUsage() + " " + number;
                    //String newPage = String.valueOf(page);
                    //String newNumber = String.valueOf(number);

                    //this.plugin.getAdventure().hover(context.getPlayer(), footer.replaceAll("\\{page}", newPage),
                    //        text.replaceAll("\\{page}", newNumber), this.locale.pageNextButton(), fullUsage,
                    //        ClickEvent.Action.RUN_COMMAND);
                }
            } else {
                context.reply(footer.replaceAll("\\{page}", String.valueOf(page)));
            }
        }

        this.lastPage = max >= this.totalResults;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public void setPerPage(int perPage) {
        this.perPage = perPage;
    }

    public void setPage(int page, int perPage) {
        this.setPage(page);
        this.setPerPage(perPage);
    }

    public int getPage() {
        return this.page;
    }

    public int getPerPage() {
        return this.perPage;
    }

    public int getTotalResults() {
        return this.totalResults;
    }

    public int getTotalPages() {
        return this.totalPages;
    }

    public boolean isLastPage() {
        return this.lastPage;
    }
}