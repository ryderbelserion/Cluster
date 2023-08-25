package com.ryderbelserion.ruby.paper.storage.objects;

public record CustomCommand(String subCommand) {

    @Override
    public String subCommand() {
        return this.subCommand;
    }
}