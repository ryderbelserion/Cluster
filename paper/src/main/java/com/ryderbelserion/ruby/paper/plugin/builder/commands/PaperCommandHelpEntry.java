package com.ryderbelserion.ruby.paper.plugin.builder.commands;

import com.ryderbelserion.ruby.other.builder.ComponentBuilder;
import com.ryderbelserion.ruby.other.builder.commands.args.Argument;
import com.ryderbelserion.ruby.paper.PaperPlugin;
import com.ryderbelserion.ruby.paper.plugin.registry.PaperProvider;
import net.kyori.adventure.text.event.ClickEvent;
import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;
import java.util.Comparator;

public class PaperCommandHelpEntry {

    private final @NotNull PaperPlugin plugin = PaperProvider.get();

    private final @NotNull PaperCommandManager manager = this.plugin.getManager();

    private int page = 1;
    private int perPage;
    private int totalPages;
    private int totalResults;
    private boolean lastPage;

    public PaperCommandHelpEntry() {

    }

    public void showHelp(PaperCommandContext context) {
        int min = this.perPage * (this.page - 1);
        int max = min + this.perPage;

        this.totalResults = this.manager.getCommands().size();

        this.totalPages = this.totalResults / this.perPage;

        if (min >= this.totalResults) {
            //TODO() Add reply message which replaces {page}
            return;
        }

        for (int value = min; value < max; value++) {
            if (this.totalResults - 1 < value) continue;

            PaperCommandEngine command = this.manager.getClasses().get(value);

            boolean isVisible = command.isVisible();

            if (!isVisible || !command.isRegistered()) return;

            StringBuilder baseFormat = new StringBuilder(command.getUsage());

            //TODO() Add locale provider.
            String format = " ";

            if (!command.getAliases().isEmpty()) baseFormat.append(" ").append(command.getAliases().get(0));

            ArrayList<Argument> arguments = new ArrayList<>();

            arguments.addAll(command.getOptionalArgs(command));
            arguments.addAll(command.getRequiredArgs(command));

            arguments.sort(Comparator.comparing(Argument::order));

            // Check if player.
            if (context.isPlayer()) {
                StringBuilder types = new StringBuilder();

                ComponentBuilder builder = new ComponentBuilder();

                for (Argument args : arguments) {
                    String arg = command.getOptionalArgs(command).contains(args) ? " (" + args.name() + ") " : " <" + args.name() + ">";
                    types.append(arg);
                }

                builder.setMessage(format.replace("\\{args}", String.valueOf(types)));

                String hoverShit = baseFormat.append(types).toString();

                String hoverFormat = " ";

                //builder.hover(hoverFormat.replaceAll("\\{command}", hoverShit)).click(hoverShit, ClickEvent.Action.COPY_TO_CLIPBOARD);
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