package com.ryderbelserion.cluster.plugin.commands;

import com.ryderbelserion.cluster.bukkit.items.ParentBuilder;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class ClusterCommand extends Command {

    public ClusterCommand() {
        super("cluster");
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        Player player = (Player) sender;

        ItemStack firework = ParentBuilder.of(Material.FIREWORK_ROCKET)
                .setFireworkPower(2)
                .setEffect(FireworkEffect.builder().trail(true).with(FireworkEffect.Type.CREEPER).withFade(Color.AQUA)
                        .withColor(Color.OLIVE, Color.fromRGB(235, 42, 81)))
                .build();

        player.getInventory().addItem(firework);

        return true;
    }
}