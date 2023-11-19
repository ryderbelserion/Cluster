package com.ryderbelserion.cluster.items;

import com.google.gson.annotations.Expose;
import com.ryderbelserion.cluster.paper.ClusterService;
import com.ryderbelserion.cluster.paper.items.ItemBuilder;
import com.ryderbelserion.cluster.paper.items.NbtBuilder;
import com.ryderbelserion.cluster.paper.items.ParentBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import java.util.List;

public class SerializableItem {

    private final ItemStack itemStack;
    private final NbtBuilder nbtBuilder;

    private ItemBuilder itemBuilder;

    @Expose
    private Material material;
    @Expose
    private String displayName;
    @Expose
    private List<String> displayLore;
    @Expose
    private int amount;
    @Expose
    private String skullName;

    public SerializableItem(Material material, String displayName, List<String> displayLore, int amount) {
        this.material = material;
        this.displayName = displayName;
        this.displayLore = displayLore;
        this.amount = amount;

        this.itemBuilder = ParentBuilder.of().setMaterial(material).setDisplayName(displayName).setDisplayLore(displayLore).setAmount(amount);

        this.nbtBuilder = new NbtBuilder(ClusterService.get().getPlugin(), this.itemStack = this.itemBuilder.build());
    }

    public SerializableItem(Material material, String displayName, List<String> displayLore, int amount, String skullName) {
        this.material = material;
        this.displayName = displayName;
        this.displayLore = displayLore;
        this.amount = amount;
        this.skullName = skullName;

        this.itemBuilder = ParentBuilder.of().setMaterial(material).setDisplayName(displayName).setDisplayLore(displayLore).setAmount(amount).setPlayer(skullName);

        this.nbtBuilder = new NbtBuilder(ClusterService.get().getPlugin(), this.itemStack = this.itemBuilder.build());
    }

    public Material getMaterial() {
        return this.material;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public List<String> getDisplayLore() {
        return this.displayLore;
    }

    public int getAmount() {
        return this.amount;
    }

    public String getSkullName() {
        return this.skullName;
    }

    public ItemStack getItemStack() {
        return this.itemStack;
    }

    public NbtBuilder getNbt() {
        return this.nbtBuilder;
    }

    public void deconstruct(ItemBuilder builder) {
        this.itemBuilder = ParentBuilder.of(builder);
    }
}