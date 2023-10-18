package com.ryderbelserion.cluster.listeners;

import com.ryderbelserion.cluster.TestPlugin;
import com.ryderbelserion.cluster.paper.items.NbtBuilder;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class StructureInteractEvent implements Listener {

    private final TestPlugin plugin;

    public StructureInteractEvent(TestPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        if (event.getItem() == null) return;

        NbtBuilder nbtBuilder = new NbtBuilder(this.plugin, event.getItem());

        // It's not a wand so return.
        if (!nbtBuilder.hasString("structure_wand")) return;

        String uuid = nbtBuilder.getString("structure_wand");

        if (player.getUniqueId().toString().equals(uuid)) {
            if (event.getClickedBlock() == null) return;

            if (this.plugin.getBlocks().contains(event.getClickedBlock().getLocation())) {
                this.plugin.getBlocks().remove(event.getClickedBlock().getLocation());
                this.plugin.getBlocks().add(event.getClickedBlock().getLocation());

                player.sendMessage(this.plugin.getPlugin().parse("<red>This location is already in the arraylist so it has been updated."));

                event.setCancelled(true);

                return;
            }

            Location location = event.getClickedBlock().getLocation();

            this.plugin.getBlocks().add(location);

            player.sendMessage(this.plugin.getPlugin().parse("<green>You have added a new location."));

            event.setCancelled(true);
        }
    }
}