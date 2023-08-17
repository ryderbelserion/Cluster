package com.ryderbelserion.ruby.paper.plugin.builder.commands;

import com.ryderbelserion.ruby.paper.PaperImpl;
import com.ryderbelserion.ruby.other.builder.commands.CommandContext;
import com.ryderbelserion.ruby.paper.plugin.registry.PaperProvider;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import java.util.List;

public class PaperCommandContext extends CommandContext {

    private final @NotNull PaperImpl paper = PaperProvider.get();

    private final @NotNull JavaPlugin plugin = this.paper.getPlugin();

    private final CommandSender sender;
    private final List<String> args;
    private String label;

    public PaperCommandContext(CommandSender sender, String label, List<String> args) {
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