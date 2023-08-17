package com.ryderbelserion.ruby.minecraft;

import com.ryderbelserion.ruby.minecraft.plugin.Logger;
import com.ryderbelserion.ruby.minecraft.plugin.Platform;
import com.ryderbelserion.ruby.other.registry.RubyRegistration;
import com.ryderbelserion.ruby.minecraft.plugin.Adventure;
import com.ryderbelserion.ruby.minecraft.utils.FileUtil;
import net.kyori.adventure.platform.AudienceProvider;

public abstract class RubyImpl {

    public abstract AudienceProvider getAudience();

    public abstract Platform.Type getPlatform();

    public abstract Adventure getAdventure();

    public abstract FileUtil getFileUtil();

    public abstract Logger getLogger();

    public abstract String getPrefix();

    public void enable() {
        RubyRegistration.start(this);
    }

    public void disable() {
        RubyRegistration.stop();
    }
}