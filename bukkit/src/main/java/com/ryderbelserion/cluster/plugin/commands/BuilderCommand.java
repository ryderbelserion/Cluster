package com.ryderbelserion.cluster.plugin.commands;

import com.ryderbelserion.cluster.bukkit.items.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;
import java.util.*;

public class BuilderCommand extends Command {

    public BuilderCommand() {
        super("builder");
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        String type = args[0];

        String material = args[1];

        if (material.isEmpty() || material.isBlank()) return false;

        Material item = Material.matchMaterial(material);

        if (item != null) {
            ItemStack itemStack = new ItemStack(item);

            ItemBuilder builder = new ItemBuilder(itemStack);

            switch (type) {
                case "item-data" -> builder.setItemData("{Enchantments:[{id:\"minecraft:fire_aspect\",lvl:2s},{id:\"minecraft:sharpness\",lvl:5s},{id:\"minecraft:unbreaking\",lvl:3s}],Damage:0,slots:2,\"ae_enchantment;doublestrike\":1,\"ae_enchantment;disarmor\":1}");

                case "enchants" -> {
                    String enchant = args[2];

                    if (!enchant.isBlank() || !enchant.isEmpty()) {
                        Enchantment enchantment = Enchantment.getByKey(NamespacedKey.fromString(enchant));

                        String level = args[3];

                        if (level.isEmpty() || level.isBlank()) {
                            if (enchantment != null) builder.addEnchantment(enchantment, 1, true);
                        } else {
                            if (enchantment != null) builder.addEnchantment(enchantment, Integer.parseInt(level), true);
                        }
                    }
                }
            }

            Player player = (Player) sender;

            player.getInventory().clear();

            player.getInventory().addItem(builder.build());
        }

        return true;
    }

    private static final List<String> ITEMS = Arrays.stream(Material.values()).filter(Material::isItem).map(Enum::name).toList();

    private static List<String> getEnchants(ItemStack itemStack) {
        return Arrays.stream(Enchantment.values()).filter(ench -> ench.canEnchantItem(itemStack)).map(key -> key.getKey().getKey()).toList();
    }

    private List<String> getNumbers() {
        List<String> numbers = new ArrayList<>();

        for (int value = 1; value <= 100; value += 1) numbers.add(String.valueOf(value));

        return numbers;
    }

    @Override
    public @NotNull List<String> tabComplete(@NotNull CommandSender sender, @NotNull String alias, @NotNull String[] args) throws IllegalArgumentException {
        if (args.length == 1) {
            return StringUtil.copyPartialMatches(args[0].toLowerCase(), List.of(
                    "enchants",
                    "item-data",
                    "lores"
            ), new ArrayList<>());
        }

        if (args.length == 2) {
            return StringUtil.copyPartialMatches(args[1].toLowerCase(), ITEMS, new ArrayList<>());
        }

        switch (args[0]) {
            case "enchants" -> {
                if (args.length == 3) {
                    ItemStack itemStack = new ItemStack(Objects.requireNonNull(Material.matchMaterial(args[1])));

                    return StringUtil.copyPartialMatches(args[2].toLowerCase(), getEnchants(itemStack), new ArrayList<>());
                }

                if (args.length == 4) {
                    return StringUtil.copyPartialMatches(args[3], getNumbers(), new ArrayList<>());
                }
            }

            case "other" -> {

            }
        }

        return Collections.emptyList();
    }
}