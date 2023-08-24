package com.ryderbelserion.ruby.paper.plugin.commands;

import com.ryderbelserion.ruby.other.commands.CommandContext;
import com.ryderbelserion.ruby.paper.PaperPlugin;
import com.ryderbelserion.ruby.paper.plugin.commands.args.CommandArgs;
import com.ryderbelserion.ruby.paper.plugin.registry.PaperProvider;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class PaperCommandContext extends CommandContext implements CommandArgs {

    private final @NotNull PaperPlugin paper = PaperProvider.get();

    private final @NotNull JavaPlugin plugin = this.paper.getPlugin();
    private final boolean isLegacy = this.paper.isLegacy();

    private final CommandSender sender;

    public PaperCommandContext(CommandSender sender, String label, List<String> args) {
        super(sender, label, args);

        this.sender = sender;
    }

    public void legacy(String message) {
        this.sender.sendMessage(this.paper.getLegacyUtil().parse(message));
    }

    @Override
    public CommandSender getSender() {
        return this.sender;
    }

    public Player getPlayer() {
        return (Player) this.sender;
    }

    public boolean isPlayer() {
        return this.sender instanceof Player;
    }

    @Override
    public Integer getArgAsInt(int index, boolean notifySender, String message) {
        int value;

        try {
            value = Integer.parseInt(getArgs().get(index));
        } catch (NumberFormatException exception) {
            String msg = message.replaceAll("\\{value}", getArgs().get(index)).replaceAll("\\{action}", "integer");

            if (!notifySender) return null;

            if (this.isLegacy) {
                legacy(msg);
                return null;
            }

            reply(msg);
            return null;
        }

        return value;
    }

    @Override
    public Long getArgAsLong(int index, boolean notifySender, String message) {
        long value;

        try {
            value = Long.parseLong(getArgs().get(index));
        } catch (NumberFormatException exception) {
            String msg = message.replaceAll("\\{value}", getArgs().get(index)).replaceAll("\\{action}", "long");

            if (!notifySender) return null;

            if (this.isLegacy) {
                legacy(msg);
                return null;
            }

            reply(msg);
            return null;
        }

        return value;
    }

    @Override
    public Double getArgAsDouble(int index, boolean notifySender, String message) {
        double value;

        try {
            value = Double.parseDouble(getArgs().get(index));
        } catch (NumberFormatException exception) {
            String msg = message.replaceAll("\\{value}", getArgs().get(index)).replaceAll("\\{action}", "double");

            if (!notifySender) return null;

            if (this.isLegacy) {
                legacy(msg);
                return null;
            }

            reply(msg);
            return null;
        }

        return value;
    }

    @Override
    public Boolean getArgAsBoolean(int index, boolean notifySender, String message) {
        String lowerCase = getArgs().get(index).toLowerCase();

        switch (lowerCase) {
            case "true", "on", "1" -> {
                return true;
            }
            case "false", "off", "0" -> {
                return false;
            }
            default -> {
                String msg = message.replaceAll("\\{value}", getArgs().get(index)).replaceAll("\\{action}", "boolean");

                if (!notifySender) return null;

                if (this.isLegacy) {
                    legacy(msg);
                    return null;
                }

                reply(msg);
                return null;
            }
        }
    }

    @Override
    public Float getArgAsFloat(int index, boolean notifySender, String message) {
        float value;

        try {
            value = Float.parseFloat(getArgs().get(index));
        } catch (NumberFormatException exception) {
            String msg = message.replaceAll("\\{value}", getArgs().get(index)).replaceAll("\\{action}", "float");

            if (!notifySender) return null;

            if (this.isLegacy) {
                legacy(msg);
                return null;
            }

            reply(msg);
            return null;
        }

        return value;
    }

    @Override
    public Player getArgAsPlayer(int index, boolean notifySender, String message) {
        Player player = this.paper.getPlugin().getServer().getPlayer(getArgs().get(index));

        if (player == null) {
            String msg = message.replaceAll("\\{value}", getArgs().get(index)).replaceAll("\\{action}", "player");

            if (!notifySender) return null;

            if (this.isLegacy) {
                legacy(msg);
                return null;
            }

            reply(msg);
            return null;
        }

        return player;
    }

    @Override
    public OfflinePlayer getArgAsOfflinePlayer(int index) {
        CompletableFuture<UUID> future = CompletableFuture.supplyAsync(() -> this.plugin.getServer().getOfflinePlayer(getArgs().get(index))).thenApply(OfflinePlayer::getUniqueId);

        return this.plugin.getServer().getOfflinePlayer(future.join());
    }
}