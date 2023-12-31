package com.ryderbelserion.cluster.utils.modules;

import org.bukkit.event.Listener;

public abstract class ModuleHandler implements Listener {

    public abstract String getModuleName();

    public abstract boolean isEnabled();

    public abstract void reload();

}