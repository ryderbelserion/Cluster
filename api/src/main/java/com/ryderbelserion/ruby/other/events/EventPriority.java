package com.ryderbelserion.ruby.other.events;

public enum EventPriority {

    // Obliterated
    DEFCON_0(0),
    // Fucked
    DEFCON_1(1),
    // Pretty close to fucked
    DEFCON_2(2),
    // Almost fucked
    DEFCON_3(3),
    // Slightly good
    DEFCON_4(4),
    // We good
    DEFCON_5(5);

    private final int level;

    EventPriority(int level) {
        this.level = level;
    }

    public int getLevel() {
        return this.level;
    }
}