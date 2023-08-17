package com.ryderbelserion.ruby.paper;

import com.ryderbelserion.ruby.minecraft.RubyImpl;
import com.ryderbelserion.ruby.minecraft.plugin.Logger;
import com.ryderbelserion.ruby.minecraft.plugin.Platform;
import com.ryderbelserion.ruby.minecraft.plugin.Adventure;
import com.ryderbelserion.ruby.minecraft.utils.FileUtil;
import com.ryderbelserion.ruby.paper.plugin.builder.commands.PaperCommandManager;
import com.ryderbelserion.ruby.paper.plugin.registry.PaperRegistration;
import com.ryderbelserion.ruby.paper.utils.ItemUtil;
import com.ryderbelserion.ruby.paper.utils.LegacyUtil;
import net.kyori.adventure.platform.AudienceProvider;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import org.bukkit.plugin.java.JavaPlugin;

public class PaperImpl extends RubyImpl {

    private PaperCommandManager manager;
    private BukkitAudiences audience;
    private LegacyUtil legacyUtil;
    private Adventure adventure;
    private JavaPlugin plugin;
    private ItemUtil itemUtil;
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
        this.legacyUtil = new LegacyUtil();
        this.logger = new Logger(this.plugin.getName());

        this.itemUtil = new ItemUtil();

        this.fileUtil = new FileUtil();

        if (!this.plugin.getDataFolder().exists()) this.plugin.getDataFolder().mkdirs();

        this.manager = new PaperCommandManager();
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

    public PaperCommandManager getManager() {
        return this.manager;
    }

    @Override
    public AudienceProvider getAudience() {
        return this.audience;
    }

    @Override
    public Platform.Type getPlatform() {
        return Platform.Type.PAPER;
    }

    @Override
    public Adventure getAdventure() {
        return this.adventure;
    }

    @Override
    public FileUtil getFileUtil() {
        return this.fileUtil;
    }

    @Override
    public Logger getLogger() {
        return this.logger;
    }

    @Override
    public String getPrefix() {
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

    public LegacyUtil getLegacyUtil() {
        return this.legacyUtil;
    }

    public ItemUtil getItemUtils() {
        return this.itemUtil;
    }

    public JavaPlugin getPlugin() {
        return this.plugin;
    }
}