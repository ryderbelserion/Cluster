package com.ryderbelserion.cluster.plugin;

import com.ryderbelserion.cluster.bukkit.BukkitPlugin;
import com.ryderbelserion.cluster.plugin.commands.ClusterCommand;
import org.bukkit.command.Command;
import org.bukkit.plugin.java.JavaPlugin;

public class Cluster extends JavaPlugin {

    private BukkitPlugin bukkitPlugin;

    @Override
    public void onEnable() {
        this.bukkitPlugin = new BukkitPlugin(this);
        this.bukkitPlugin.enable();

        Command command = new ClusterCommand();

        getServer().getCommandMap().register("cluster", command);
    }

    @Override
    public void onDisable() {
        this.bukkitPlugin.disable();
    }
}