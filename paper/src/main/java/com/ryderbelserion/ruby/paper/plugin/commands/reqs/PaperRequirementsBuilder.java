package com.ryderbelserion.ruby.paper.plugin.commands.reqs;

import org.bukkit.permissions.Permission;

public class PaperRequirementsBuilder {

    private boolean isPlayer = false;
    private Permission permission = null;
    private String rawPermission = "";

    public PaperRequirementsBuilder isPlayer(boolean isPlayer) {
        this.isPlayer = isPlayer;

        return this;
    }

    public boolean isPlayer() {
        return this.isPlayer;
    }

    public PaperRequirementsBuilder withRawPermission(String rawPermission) {
        this.rawPermission = rawPermission;

        return this;
    }

    public String getRawPermission() {
        return this.rawPermission;
    }

    public PaperRequirementsBuilder withPermission(Permission permission) {
        this.permission = permission;

        return this;
    }

    public Permission getPermission() {
        return this.permission;
    }

    public PaperRequirements build() {
        return new PaperRequirements(this.isPlayer, this.permission, this.rawPermission);
    }
}