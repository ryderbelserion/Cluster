package com.ryderbelserion.cluster.bukkit.gui;

import com.ryderbelserion.cluster.bukkit.api.utils.ColorUtils;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public abstract class GuiBuilder implements InventoryHolder {

    private final Inventory inventory;
    private String title;
    private int size;

    public GuiBuilder(JavaPlugin plugin, int size, String title) {
        this.title = title;
        this.size = size;

        this.inventory = plugin.getServer().createInventory(this, this.size, ColorUtils.parse(this.title));
    }

    public abstract GuiBuilder build();

    public void size(int size) {
        this.size = size;
    }

    public void title(String title) {
        this.title = title;
    }

    public void update(HumanEntity human) {
        human.closeInventory(InventoryCloseEvent.Reason.OPEN_NEW);

        human.openInventory(getInventory());
    }

    @Override
    public @NotNull Inventory getInventory() {
        return this.inventory;
    }
}