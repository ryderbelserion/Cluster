package com.ryderbelserion.ruby.minecraft;

import com.ryderbelserion.ruby.minecraft.registry.RubyRegistration;
import com.ryderbelserion.ruby.minecraft.utils.ColorUtils;
import com.ryderbelserion.ruby.minecraft.utils.FileUtil;
import com.ryderbelserion.ruby.other.Platform;
import com.ryderbelserion.ruby.other.config.FileManager;
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