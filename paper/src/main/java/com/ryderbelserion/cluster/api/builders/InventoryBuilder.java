package com.ryderbelserion.cluster.api.builders;

import com.ryderbelserion.cluster.utils.AdvUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;

public abstract class InventoryBuilder implements InventoryHolder {

    private final Inventory inventory;
    private final Player player;
    private String title;
    private int size;
    private int page;

    public InventoryBuilder(Player player, int size, String title) {
        this.title = title;
        this.player = player;
        this.size = size;

        this.inventory = Bukkit.getServer().createInventory(this, this.size, AdvUtil.parse(this.title));
    }

    public abstract InventoryBuilder build();

    public void open() {
        this.player.openInventory(getInventory());
    }

    public void size(int size) {
        this.size = size;
    }

    public int getSize() {
        return this.size;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPage() {
        return this.page;
    }

    public void title(String title) {
        this.title = title;
    }

    public boolean contains(String message) {
        return this.title.contains(message);
    }

    public Player getPlayer() {
        return this.player;
    }

    @Override
    @NotNull
    public Inventory getInventory() {
        return this.inventory;
    }
}