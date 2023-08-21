package com.ryderbelserion.ruby.minecraft;

import com.ryderbelserion.ruby.minecraft.plugin.FancyLogger;
import com.ryderbelserion.ruby.minecraft.plugin.Platform;
import com.ryderbelserion.ruby.minecraft.plugin.registry.RubyRegistration;
import com.ryderbelserion.ruby.minecraft.utils.AdvUtil;
import com.ryderbelserion.ruby.minecraft.utils.FileUtil;
import net.kyori.adventure.platform.AudienceProvider;

public abstract class RubyPlugin {

    public abstract AudienceProvider getAudience();

    public abstract Platform.Type getPlatform();

    public abstract AdvUtil getAdventure();

    public abstract FileUtil getFileUtil();

    public abstract FancyLogger getFancyLogger();

    public abstract boolean isLegacy();

    public void enable(boolean value) {
        RubyRegistration.start(this);
    }

    public void disable() {
        RubyRegistration.stop();
    }
}