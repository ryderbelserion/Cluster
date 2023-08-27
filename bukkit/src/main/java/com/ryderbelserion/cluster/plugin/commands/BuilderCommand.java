package com.ryderbelserion.cluster.plugin.commands;

import com.ryderbelserion.cluster.bukkit.items.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class BuilderCommand extends Command {

    public BuilderCommand() {
        super("builder");
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        String material = args[0];

        if (material.isEmpty() || material.isBlank()) return false;

        Material item = Material.matchMaterial(material);

        if (item != null) {
            ItemStack itemStack = new ItemStack(item);

            ItemBuilder builder = new ItemBuilder(itemStack);

            Player player = (Player) sender;

            player.getInventory().clear();

            player.getInventory().addItem(builder.build());
        }

        return true;
    }

    private static final List<String> ITEMS = Arrays.stream(Material.values()).filter(Material::isItem).map(Enum::name).toList();

    @Override
    public @NotNull List<String> tabComplete(@NotNull CommandSender sender, @NotNull String alias, @NotNull String[] args) throws IllegalArgumentException {
        if (args.length == 1) {
            return StringUtil.copyPartialMatches(args[0].toLowerCase(), ITEMS, new ArrayList<>());
        }

        return Collections.emptyList();
    }
}