package com.ryderbelserion.ruby.minecraft;

import com.ryderbelserion.ruby.minecraft.plugin.Logger;
import com.ryderbelserion.ruby.minecraft.plugin.Platform;
import com.ryderbelserion.ruby.minecraft.plugin.registry.RubyRegistration;
import com.ryderbelserion.ruby.minecraft.utils.Adventure;
import net.kyori.adventure.platform.AudienceProvider;

public abstract class RubyImpl {

    public abstract AudienceProvider audience();

    public abstract Platform.Type platform();

    public abstract Adventure adventure();

    public abstract Logger logger();

    public void enable() {
        RubyRegistration.start(this);
    }

    public void disable() {
        RubyRegistration.stop();
    }
}