package com.ryderbelserion.ruby;

import com.ryderbelserion.ruby.minecraft.RubyImpl;
import com.ryderbelserion.ruby.minecraft.plugin.Logger;
import com.ryderbelserion.ruby.minecraft.plugin.Platform;
import com.ryderbelserion.ruby.minecraft.plugin.Adventure;
import com.ryderbelserion.ruby.minecraft.utils.FileUtil;
import com.ryderbelserion.ruby.plugin.registry.PaperRegistration;
import com.ryderbelserion.ruby.utils.ItemUtils;
import net.kyori.adventure.platform.AudienceProvider;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import org.bukkit.plugin.java.JavaPlugin;

public class PaperImpl extends RubyImpl {

    private JavaPlugin plugin;

    private BukkitAudiences audience;
    private Adventure adventure;
    private ItemUtils itemUtils;
    private FileUtil fileUtil;
    private Logger logger;

    public PaperImpl(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void enable() {
        // Runs the enable method from the inherited class.
        super.enable();

        // Initializes the paper provider.
        PaperRegistration.start(this);

        // Creates the audience sender.
        if (this.audience == null) {
            this.audience = BukkitAudiences.create(this.plugin);
        }

        // Create adventure/logger instance.
        this.adventure = new Adventure();
        this.logger = new Logger(this.plugin.getName());

        this.itemUtils = new ItemUtils();

        this.fileUtil = new FileUtil();

        if (!this.plugin.getDataFolder().exists()) this.plugin.getDataFolder().mkdirs();
    }

    @Override
    public void disable() {
        // Runs the disable method from the inherited class.
        super.disable();

        // Stops the paper provider.
        PaperRegistration.stop();

        // If audience is not null, close and set to null.
        if (this.audience != null) {
            this.audience.close();
            this.audience = null;
        }
    }

    @Override
    public AudienceProvider audience() {
        return this.audience;
    }

    @Override
    public Platform.Type platform() {
        return Platform.Type.PAPER;
    }

    @Override
    public Adventure adventure() {
        return this.adventure;
    }

    @Override
    public FileUtil fileUtil() {
        return this.fileUtil;
    }

    @Override
    public Logger logger() {
        return this.logger;
    }

    @Override
    public String prefix() {
        return "[" + this.plugin.getName() + "] ";
    }

    public PaperImpl setPlugin(JavaPlugin plugin) {
        // If the plugin is already registered,
        // return as we don't want it registered again.
        if (this.plugin != null) return this;

        // Set the plugin variable.
        this.plugin = plugin;

        return this;
    }

    public ItemUtils itemUtils() {
        return this.itemUtils;
    }

    public JavaPlugin getPlugin() {
        return this.plugin;
    }
}