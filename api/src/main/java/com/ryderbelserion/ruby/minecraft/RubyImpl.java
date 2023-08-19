package com.ryderbelserion.ruby.minecraft;

import com.ryderbelserion.ruby.minecraft.plugin.Logger;
import com.ryderbelserion.ruby.minecraft.plugin.Platform;
import com.ryderbelserion.ruby.other.config.MessageKey;
import com.ryderbelserion.ruby.other.registry.RubyRegistration;
import com.ryderbelserion.ruby.minecraft.plugin.Adventure;
import com.ryderbelserion.ruby.minecraft.utils.FileUtil;
import net.kyori.adventure.platform.AudienceProvider;

public abstract class RubyImpl {

    public abstract AudienceProvider getAudience();

    public abstract MessageKey getMessageKey();

    public abstract void setMessageKey(MessageKey messageKey);

    public abstract Platform.Type getPlatform();

    public abstract Adventure getAdventure();

    public abstract FileUtil getFileUtil();

    public abstract Logger getLogger();

    public abstract String getPrefix();

    public abstract boolean isLegacy();

    public void enable(boolean value) {
        RubyRegistration.start(this);
    }

    public void disable() {
        RubyRegistration.stop();
    }
}