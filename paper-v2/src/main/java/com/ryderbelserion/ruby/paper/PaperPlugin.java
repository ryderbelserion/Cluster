package com.ryderbelserion.ruby.paper;

import com.ryderbelserion.ruby.minecraft.RubyPlugin;
import com.ryderbelserion.ruby.minecraft.FancyLogger;
import com.ryderbelserion.ruby.other.Platform;
import com.ryderbelserion.ruby.minecraft.utils.ColorUtils;
import com.ryderbelserion.ruby.minecraft.utils.FileUtil;
import com.ryderbelserion.ruby.other.config.FileManager;
import com.ryderbelserion.ruby.paper.registry.PaperRegistration;
import com.ryderbelserion.ruby.paper.storage.DataManager;
import net.kyori.adventure.audience.Audience;
import org.bukkit.plugin.java.JavaPlugin;
import java.io.File;
import java.nio.file.Path;

public class PaperPlugin extends RubyPlugin {

    private DataManager dataManager;
    private FileManager fileManager;
    private ColorUtils colorUtils;
    private FancyLogger logger;
    private FileUtil fileUtil;
    private JavaPlugin plugin;
    private boolean isLegacy;
    private Path path;

    public PaperPlugin(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public void setPlugin(JavaPlugin plugin) {
        // If the plugin is already registered,
        // return as we don't want it registered again.
        if (this.plugin != null) return;

        // Set the plugin variable.
        this.plugin = plugin;
    }

    public JavaPlugin getPlugin() {
        return this.plugin;
    }

    @Override
    public void enable(boolean value) {
        super.enable(this.isLegacy = value);

        PaperRegistration.start(this);

        this.colorUtils = new ColorUtils();
        this.logger = new FancyLogger(getPlugin().getName());

        this.fileUtil = new FileUtil();
        this.fileManager = new FileManager();

        this.path = getPlugin().getServer().getPluginsFolder().toPath().resolve("Ruby");

        File file = this.path.toFile();

        if (!file.exists()) file.mkdirs(); else getFancyLogger().debug("Could not create " + file.getName() + " because it already exists");

        if (!getPlugin().getDataFolder().exists()) getPlugin().getDataFolder().mkdirs();

        this.dataManager = new DataManager(this.path);

        getFileManager().addFile(getDataManager());
    }

    public DataManager getDataManager() {
        return this.dataManager;
    }

    @Override
    public void disable() {
        super.disable();

        getFileManager().saveFile(getDataManager());

        PaperRegistration.stop();
    }

    @Override
    public FileManager getFileManager() {
        return this.fileManager;
    }

    @Override
    public FancyLogger getFancyLogger() {
        return this.logger;
    }

    @Override
    public ColorUtils getColorUtils() {
        return this.colorUtils;
    }

    @Override
    public Platform.Type getPlatform() {
        return Platform.Type.PAPER;
    }

    @Override
    public FileUtil getFileUtils() {
        return this.fileUtil;
    }

    @Override
    public Audience getConsole() {
        return getPlugin().getServer().getConsoleSender();
    }

    @Override
    public boolean isLegacy() {
        return this.isLegacy;
    }

    @Override
    public Path getPath() {
        return this.path;
    }
}