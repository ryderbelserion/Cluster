package com.ryderbelserion.cluster.command;

import com.ryderbelserion.cluster.TestPlugin;
import com.ryderbelserion.cluster.enums.Files;
import com.ryderbelserion.cluster.paper.files.CustomFile;
import com.ryderbelserion.cluster.paper.items.ItemBuilder;
import com.ryderbelserion.cluster.paper.items.ParentBuilder;
import me.arcaniax.hdb.api.HeadDatabaseAPI;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ReloadCommand extends Command {

    private final TestPlugin plugin;

    public ReloadCommand(TestPlugin plugin) {
        super("test");

        this.plugin = plugin;
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        List<String> messages = List.of(
                " <blue>Test Help Menu",
                "",
                " <green>Green</green> : Active",
                " <red>Red</red> : Inactive",
                "",
                " <green>- /test help - <reset>Shows this menu",
                " <green>- /test reload <gray>- <reset>Reloads the plugin",
                " <green>- /test skull (player only) <gray>- <reset>Tests the item builder's skull function."
        );

        if (args.length == 0) {
            messages.forEach(line -> sender.sendMessage(this.plugin.getPlugin().parse(line)));

            return true;
        }

        switch (args[0].toLowerCase()) {
            case "help" -> messages.forEach(line -> sender.sendMessage(this.plugin.getPlugin().parse(line)));

            case "reload" -> {
                String file = Files.config.getFileName();

                this.plugin.getPlugin().getFileManager().reloadStaticFile(file);

                FileConfiguration configuration = this.plugin.getPlugin().getFileManager().getStaticFile(file);

                boolean option = configuration.getBoolean("settings.test-option");

                this.plugin.getLogger().warning("Option: " + option);

                if (option) {
                    this.plugin.getLogger().warning("Enabled.");
                } else {
                    this.plugin.getLogger().warning("Not enabled.");
                }

                CustomFile customConfiguration = this.plugin.getPlugin().getFileManager().getDynamicFile("CrateExample.yml");

                customConfiguration.reload();

                boolean isEnabled = customConfiguration.getConfiguration().getBoolean("crate.enabled");

                if (isEnabled) {
                    this.plugin.getLogger().warning("Crate is enabled.");
                } else {
                    this.plugin.getLogger().warning("Crate is not enabled.");
                }

                sender.sendMessage(this.plugin.getPlugin().parse(" <red>[TestPlugin] <green>Plugin successfully reloaded."));
            }

            case "skull" -> {
                if (sender instanceof Player player) {
                    ItemBuilder skull = ParentBuilder.of(this.plugin, Material.PLAYER_HEAD).setAmount(1).setPlayer(player.getName());

                    player.getInventory().addItem(skull.build());

                    HeadDatabaseAPI api = new HeadDatabaseAPI();

                    ItemBuilder customHead = ParentBuilder.of(this.plugin, api.getItemHead("43547")).setAmount(3);

                    player.getInventory().addItem(customHead.build());

                    return true;
                }

                sender.sendMessage(this.plugin.getPlugin().parse(" <red>[TestPlugin] You must be a player to run this command."));
            }
        }

        return false;
    }

    @Override
    public @NotNull List<String> tabComplete(@NotNull CommandSender sender, @NotNull String alias, @NotNull String[] args) throws IllegalArgumentException {
        List<String> completions = new ArrayList<>();

        if (args.length == 1) {
            completions.add("help");
            completions.add("reload");
            completions.add("skull");

            return StringUtil.copyPartialMatches(args[0], completions, new ArrayList<>());
        }

        return Collections.emptyList();
    }
}