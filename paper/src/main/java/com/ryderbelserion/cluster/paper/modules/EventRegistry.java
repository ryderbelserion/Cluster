package com.ryderbelserion.cluster.paper.modules;

import com.ryderbelserion.cluster.paper.ClusterService;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;
import java.util.List;

public class EventRegistry {

    @NotNull
    private final JavaPlugin plugin = ClusterService.get().getPlugin();

    @NotNull
    private final List<Listener> listeners = new ArrayList<>();

    public void addListener(Listener listener) {
        if (this.listeners.contains(listener)) return;

        this.listeners.add(listener);
        this.plugin.getServer().getPluginManager().registerEvents(listener, this.plugin);
    }

    public void removeListener(Listener listener) {
        if (!this.listeners.contains(listener)) return;

        this.listeners.remove(listener);
        HandlerList.unregisterAll(listener);
    }
}