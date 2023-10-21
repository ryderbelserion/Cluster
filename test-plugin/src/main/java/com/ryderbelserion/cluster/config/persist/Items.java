package com.ryderbelserion.cluster.config.persist;

import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import com.ryderbelserion.cluster.api.config.context.FileData;
import com.ryderbelserion.cluster.api.config.context.FileType;
import com.ryderbelserion.cluster.items.InventoryItem;
import com.ryderbelserion.cluster.items.SerializableItem;
import com.ryderbelserion.cluster.paper.files.adapters.InventoryTypeAdapter;
import com.ryderbelserion.cluster.paper.files.adapters.LocationTypeAdapter;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import java.lang.reflect.Modifier;
import java.nio.file.Path;
import java.util.List;

public class Items extends FileData {

    public Items(Path path) {
        super(FileType.json, "items.json", path.toString(), new GsonBuilder()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .enableComplexMapKeySerialization()
                .excludeFieldsWithModifiers(Modifier.TRANSIENT)
                .excludeFieldsWithoutExposeAnnotation()
                .registerTypeAdapter(Location.class, new LocationTypeAdapter())
                .registerTypeAdapter(Inventory.class, new InventoryTypeAdapter()));
    }

    @Expose
    public static InventoryItem freeze_wand = new InventoryItem(new SerializableItem(
            Material.BLAZE_ROD,
            "<blue>Freeze Wand <gray>(Right click)",
            List.of(
                    "<gray>Freezes any player you right click."
            ),
            1
    ), 3);

    @Expose
    public static InventoryItem teleport_wand = new InventoryItem(new SerializableItem(
            Material.ENDER_CHEST,
            "<dark_purple>Teleport Wand <gray>(Right click)",
            List.of(
                    "<gray>Opens a menu that shows you a list of online players."
            ),
            1), 5);

    @Expose
    public static InventoryItem player_wand = new InventoryItem(new SerializableItem(
            Material.PLAYER_HEAD,
            "<green>Player Wand <gray>(Right click)",
            List.of(
                    "Opens a menu when you right a click a player",
                    "showing you information about the player."
            ),
            1,
            "{player}"
    ), 7);
}