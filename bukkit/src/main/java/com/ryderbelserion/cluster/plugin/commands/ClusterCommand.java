package com.ryderbelserion.cluster.plugin.commands;

import com.ryderbelserion.cluster.bukkit.items.ItemBuilder;
import com.ryderbelserion.cluster.bukkit.items.ParentBuilder;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ClusterCommand extends Command {

    public ClusterCommand() {
        super("cluster");
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        ItemBuilder builder = ParentBuilder.of(Material.PLAYER_HEAD);

        Player player = (Player) sender;

        //builder.setDisplayName("<red>Guten Tag</red>");
        //builder.setDisplayLore(List.of(
        //        "<yellow>Hello how are you?</yellow>",
        //        "<red>Doing good </red>"
        //));

        builder.setPlayer("Rukkhadevata");

        player.getInventory().addItem(builder.build());

        return true;
    }
}