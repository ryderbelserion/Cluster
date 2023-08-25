package com.ryderbelserion.ruby;

import com.ryderbelserion.ruby.adventure.FancyLogger;
import com.ryderbelserion.ruby.registry.RubyRegistration;
import com.ryderbelserion.ruby.utils.ColorUtils;
import com.ryderbelserion.ruby.utils.FileUtil;
import com.ryderbelserion.ruby.config.FileManager;
import net.kyori.adventure.audience.Audience;
import java.nio.file.Path;

public abstract class RubyPlugin {

    public abstract FileManager getFileManager();

    public abstract FancyLogger getFancyLogger();

    public abstract ColorUtils getColorUtils();

    public abstract Platform.Type getPlatform();

    public abstract FileUtil getFileUtils();

    public abstract Audience getConsole();

    public abstract boolean isLegacy();

    public abstract Path getPath();

    public void enable(boolean value) {
        RubyRegistration.start(this);
    }

    public void disable() {
        RubyRegistration.stop();
    }
}