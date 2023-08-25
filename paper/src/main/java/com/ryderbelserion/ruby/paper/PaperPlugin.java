package com.ryderbelserion.ruby.paper;

import com.ryderbelserion.ruby.minecraft.RubyPlugin;
import com.ryderbelserion.ruby.minecraft.FancyLogger;
import com.ryderbelserion.ruby.other.Platform;
import com.ryderbelserion.ruby.utils.AdvUtil;
import com.ryderbelserion.ruby.utils.FileUtil;
import com.ryderbelserion.ruby.commands.CommandHelpProvider;
import com.ryderbelserion.ruby.config.FileManager;
import com.ryderbelserion.ruby.paper.plugin.commands.PaperCommandManager;
import com.ryderbelserion.ruby.paper.plugin.items.skulls.SkullCreator;
import com.ryderbelserion.ruby.paper.plugin.registry.PaperRegistration;
import com.ryderbelserion.ruby.paper.utils.ItemUtil;
import com.ryderbelserion.ruby.paper.utils.LegacyUtil;
import net.kyori.adventure.audience.Audience;
import org.bukkit.plugin.java.JavaPlugin;

import java.nio.file.Path;

public class PaperPlugin extends RubyPlugin {

    private CommandHelpProvider helpProvider;
    private PaperCommandManager manager;
    private SkullCreator skullCreator;
    private FancyLogger fancyLogger;
    private LegacyUtil legacyUtil;
    private JavaPlugin plugin;
    private ItemUtil itemUtil;
    private FileUtil fileUtil;
    private boolean isLegacy;
    private AdvUtil advUtil;

    public PaperPlugin(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void enable(boolean value) {
        // Runs the enable method from the inherited class.
        super.enable(this.isLegacy = value);

        // Initializes the paper provider.
        PaperRegistration.start(this);

        // Create adventure/logger instance.
        this.advUtil = new AdvUtil();
        this.legacyUtil = new LegacyUtil();
        this.fancyLogger = new FancyLogger(this.plugin.getName());

        this.itemUtil = new ItemUtil();
        this.skullCreator = new SkullCreator();

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
    }

    public PaperCommandManager getManager() {
        return this.manager;
    }

    @Override
    public FileManager fileManager() {
        return null;
    }

    @Override
    public FancyLogger fancyLogger() {
        return this.fancyLogger;
    }

    @Override
    public Platform.Type platform() {
        return Platform.Type.PAPER;
    }

    @Override
    public AdvUtil adventure() {
        return this.advUtil;
    }

    @Override
    public Audience audience() {
        return this.plugin.getServer().getConsoleSender();
    }

    @Override
    public FileUtil fileUtil() {
        return this.fileUtil;
    }

    @Override
    public boolean legacy() {
        return this.isLegacy;
    }

    @Override
    public Path path() {
        return null;
    }

    public PaperPlugin setPlugin(JavaPlugin plugin) {
        // If the plugin is already registered,
        // return as we don't want it registered again.
        if (this.plugin != null) return this;

        // Set the plugin variable.
        this.plugin = plugin;

        return this;
    }

    public void setHelpProvider(CommandHelpProvider helpProvider) {
        this.helpProvider = helpProvider;
    }

    public CommandHelpProvider getHelpProvider() {
        return this.helpProvider;
    }

    public SkullCreator getSkullCreator() {
        return this.skullCreator;
    }

    public LegacyUtil getLegacyUtil() {
        return this.legacyUtil;
    }

    public ItemUtil getItemUtil() {
        return this.itemUtil;
    }

    public JavaPlugin getPlugin() {
        return this.plugin;
    }
}