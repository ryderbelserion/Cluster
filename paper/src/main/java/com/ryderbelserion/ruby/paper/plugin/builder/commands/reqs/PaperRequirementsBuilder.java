package com.ryderbelserion.ruby.paper.plugin.builder.commands.reqs;

import org.bukkit.permissions.Permission;

public class PaperRequirementsBuilder {

    private boolean isPlayer = false;
    private Permission permission = null;
    private String rawPermission = "";

    public void setPlayer(boolean player) {
        isPlayer = player;
    }

    public boolean isPlayer() {
        return this.isPlayer;
    }

    public void setRawPermission(String rawPermission) {
        this.rawPermission = rawPermission;
    }

    public String getRawPermission() {
        return this.rawPermission;
    }

    public void setPermission(Permission permission) {
        this.permission = permission;
    }

    public Permission getPermission() {
        return this.permission;
    }

    public PaperRequirements build() {
        return new PaperRequirements(this.isPlayer, this.permission, this.rawPermission);
    }
}