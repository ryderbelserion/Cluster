package com.ryderbelserion.ruby.folia.plugin.builder.commands;

import com.ryderbelserion.ruby.folia.FoliaImpl;
import com.ryderbelserion.ruby.other.builder.commands.CommandContext;
import com.ryderbelserion.ruby.paper.plugin.registry.FoliaProvider;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import java.util.List;

public class FoliaCommandContext extends CommandContext {

    private final @NotNull FoliaImpl paper = FoliaProvider.get();

    private final @NotNull JavaPlugin plugin = this.paper.getPlugin();

    private final CommandSender sender;
    private final List<String> args;
    private String label;

    public FoliaCommandContext(CommandSender sender, String label, List<String> args) {
        super(sender, label, args);

        this.sender = sender;
        this.args = args;
        this.label = label;
    }

    public void legacy(String message) {
        this.sender.sendMessage(this.paper.getLegacyUtil().parse(message));
    }

    @Override
    public CommandSender getSender() {
        return this.sender;
    }

    @Override
    public Player getPlayer() {
        return (Player) this.sender;
    }

    @Override
    public boolean isPlayer() {
        return this.sender instanceof Player;
    }

    @Override
    public List<String> getArgs() {
        return this.args;
    }

    @Override
    public String getLabel() {
        return this.label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}