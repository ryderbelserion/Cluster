package com.ryderbelserion.cluster.command;

import com.ryderbelserion.cluster.TestPlugin;
import com.ryderbelserion.cluster.paper.files.CustomFile;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.jetbrains.annotations.NotNull;

public class ReloadCommand extends Command {

    private final TestPlugin plugin;

    public ReloadCommand(TestPlugin plugin) {
        super("test");

        this.plugin = plugin;
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        this.plugin.getPlugin().getFileManager().reloadStaticFile("", "config.yml");

        FileConfiguration configuration = this.plugin.getPlugin().getFileManager().getStaticFile("config.yml");

        boolean option = configuration.getBoolean("settings.test-option");

        this.plugin.getLogger().warning("Option: " + option);

        if (option) {
            this.plugin.getLogger().warning("Enabled.");

            this.plugin.getLogger().warning("Text: " + configuration.getString("settings.test-string"));
        } else {
            this.plugin.getLogger().warning("Not enabled.");
        }

        CustomFile customConfiguration = this.plugin.getPlugin().getFileManager().getDynamicFile("CrateExample.yml");

        //this.plugin.getPlugin().getFileManager().reloadDynamicFile("CrateExample.yml");

        customConfiguration.reload();

        boolean isEnabled = customConfiguration.getConfiguration().getBoolean("crate.enabled");

        if (isEnabled) {
            this.plugin.getLogger().warning("Crate is enabled.");
        } else {
            this.plugin.getLogger().warning("Crate is not enabled.");
        }

        return false;
    }
}