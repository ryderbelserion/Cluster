package com.ryderbelserion.ruby.other;

import org.jetbrains.annotations.NotNull;

public interface Platform {

    @NotNull Type getType();

    enum Type {

        FABRIC("fabric"),
        PAPER("paper"),
        SPIGOT("spigot"),
        OTHER("other");

        private final String serverType;

        Type(String serverType) {
            this.serverType = serverType;
        }

        public @NotNull String getServerType() {
            return serverType;
        }
    }
}