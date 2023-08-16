package com.ryderbelserion.ruby.minecraft;

import com.ryderbelserion.ruby.minecraft.plugin.registry.RubyRegistration;
import net.kyori.adventure.platform.AudienceProvider;

public abstract class RubyImpl {

    public abstract AudienceProvider audience();

    public void enable() {
        RubyRegistration.start(this);
    }

    public void disable() {
        RubyRegistration.stop();
    }
}