package com.ryderbelserion.ruby.paper.plugin.commands.args;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public interface CommandArgs {

    Integer getArgAsInt(int index, boolean notifySender, String message);

    Long getArgAsLong(int index, boolean notifySender, String message);

    Double getArgAsDouble(int index, boolean notifySender, String message);

    Boolean getArgAsBoolean(int index, boolean notifySender, String message);

    Float getArgAsFloat(int index, boolean notifySender, String message);

    Player getArgAsPlayer(int index, boolean notifySender, String message);

    OfflinePlayer getArgAsOfflinePlayer(int index);

}