package com.ryderbelserion.cluster.api.builders;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.bossbar.BossBar;

public class BossBarBuilder {

    private final Audience target;

    private BossBar bar;

    public BossBarBuilder(Audience target, BossBar bar) {
        this.target = target;

        this.bar = bar;
    }

    /**
     * @return get the target recipient.
     */
    public Audience getTarget() {
        return this.target;
    }

    /**
     * @return the bossbar.
     */
    public BossBar getBar() {
        return this.bar;
    }

    /**
     * Sets the bossbar
     *
     * @param bar the new bossbar.
     */
    public void setBar(BossBar bar) {
        this.bar = bar;
    }

    /**
     * Hides the boss bar then sets the variable to null.
     */
    public void hideBar() {
        if (getBar() != null) {
            getTarget().hideBossBar(getBar());

            setBar(null);
        }
    }
}