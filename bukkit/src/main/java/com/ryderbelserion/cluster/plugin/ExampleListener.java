package com.ryderbelserion.cluster.plugin;

import com.ryderbelserion.cluster.bukkit.items.ItemBuilder;
import com.ryderbelserion.cluster.bukkit.items.ParentBuilder;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import java.util.List;

public class ExampleListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        ItemBuilder itemBuilder = ParentBuilder.of(Material.DIAMOND_SWORD).setDisplayLore(List.of(
                "Guten Tag!"
        ));

        event.getPlayer().getInventory().addItem(itemBuilder.build());
    }
}