package com.ryderbelserion.ruby.paper.plugin.builder.commands.args.types;

import com.ryderbelserion.ruby.paper.PaperImpl;
import com.ryderbelserion.ruby.other.builder.commands.args.ArgumentType;
import com.ryderbelserion.ruby.paper.plugin.registry.PaperProvider;
import org.bukkit.entity.Player;
import java.util.List;
import java.util.stream.Collectors;

public class PlayerArgument extends ArgumentType {

    private final PaperImpl paper = PaperProvider.get();

    @Override
    public List<String> getPossibleValues() {
        return this.paper.getPlugin().getServer().getOnlinePlayers().stream().map(Player::getName).collect(Collectors.toList());
    }
}