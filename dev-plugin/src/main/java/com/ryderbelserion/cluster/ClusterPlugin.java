package com.ryderbelserion.cluster;

import com.ryderbelserion.cluster.items.ItemBuilder;
import com.ryderbelserion.cluster.items.ParentBuilder;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.List;

public class ClusterPlugin extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        //ClusterFactory factory = new ClusterFactory(this, true);
        //factory.enable();

        getServer().getPluginManager().registerEvents(this, this);
    }

    @Override
    public void onDisable() {

    }

    @EventHandler(ignoreCancelled = true)
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        ItemBuilder builder = ParentBuilder.of(Registry.MATERIAL.get(NamespacedKey.minecraft("diamond_helmet")));

        builder.addEnchantment(
                        "protection",
                        5,
                        true)
                .addEnchantment("thorns",
                        3,
                        true)
                .addEnchantment("unbreaking",
                        4,
                        true);

        builder.setTrimMaterial("amethyst").setTrimPattern("vex");

        builder.setDisplayName("<red>A fancy item");

        builder.setDisplayLore(List.of(
                "<green>Another line!",
                "<rainbow>This is a rainbow!"
        ));

        builder.setItemDamage(5);

        builder.setAmount(3);

        ItemStack itemStack = builder.build();

        ItemBuilder potionBuilder = ParentBuilder.of(Registry.MATERIAL.get(NamespacedKey.minecraft("diamond_helmet")));

        player.getInventory().addItem(itemStack);
    }
}