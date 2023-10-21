package com.ryderbelserion.cluster.paper.items;

import com.ryderbelserion.cluster.paper.PaperService;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class ParentBuilder {

    public static ItemBuilder of() {
        return new ItemBuilder(PaperService.get().getPlugin());
    }

    public static ItemBuilder of(ItemStack itemStack) {
        return new ItemBuilder(PaperService.get().getPlugin(), itemStack);
    }

    public static ItemBuilder of(ItemBuilder itemBuilder) {
        return new ItemBuilder(itemBuilder);
    }

    public static ItemBuilder of(Material material) {
        return new ItemBuilder(PaperService.get().getPlugin(), new ItemStack(material));
    }
}