package com.ryderbelserion.ruby.paper.plugin.commands.args.types;

import com.ryderbelserion.ruby.other.commands.args.ArgumentType;
import com.ryderbelserion.ruby.paper.PaperPlugin;
import com.ryderbelserion.ruby.paper.plugin.registry.PaperProvider;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import java.util.List;
import java.util.stream.Collectors;

public class PlayerArgument extends ArgumentType {

    private final @NotNull PaperPlugin paper = PaperProvider.get();

    @Override
    public List<String> getPossibleValues() {
        return this.paper.getPlugin().getServer().getOnlinePlayers().stream().map(Player::getName).collect(Collectors.toList());
    }
}