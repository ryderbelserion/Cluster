package com.ryderbelserion.ruby.minecraft;

import com.ryderbelserion.ruby.minecraft.plugin.FancyLogger;
import com.ryderbelserion.ruby.minecraft.plugin.Platform;
import com.ryderbelserion.ruby.minecraft.plugin.registry.RubyRegistration;
import com.ryderbelserion.ruby.minecraft.utils.AdvUtil;
import com.ryderbelserion.ruby.minecraft.utils.FileUtil;
import net.kyori.adventure.audience.Audience;

public abstract class RubyPlugin {

    public abstract FancyLogger getFancyLogger();

    public abstract Platform.Type getPlatform();

    public abstract Audience getAudience();

    public abstract FileUtil getFileUtil();

    public abstract AdvUtil getAdventure();

    public abstract boolean isLegacy();

    public void enable(boolean value) {
        RubyRegistration.start(this);
    }

    public void disable() {
        RubyRegistration.stop();
    }
}