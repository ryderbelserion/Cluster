package com.ryderbelserion.ruby;

import net.kyori.adventure.platform.AudienceProvider;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import org.bukkit.plugin.java.JavaPlugin;

public class PaperImpl extends RubyImpl {

    private JavaPlugin plugin;

    private BukkitAudiences audience;

    public PaperImpl(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void enable() {
        super.enable();

        if (this.audience == null) {
            this.audience = BukkitAudiences.create(this.plugin);
        }
    }

    @Override
    public void disable() {
        super.disable();

        if (this.audience != null) {
            this.audience.close();
            this.audience = null;
        }
    }

    @Override
    public AudienceProvider audience() {
        return this.audience;
    }

    public PaperImpl setPlugin(JavaPlugin plugin) {
        if (this.plugin != null) return this;

        this.plugin = plugin;

        return this;
    }

    public JavaPlugin getPlugin() {
        return this.plugin;
    }
}