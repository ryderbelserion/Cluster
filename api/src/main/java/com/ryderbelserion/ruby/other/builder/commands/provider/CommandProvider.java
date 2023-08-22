package com.ryderbelserion.ruby.other.builder.commands.provider;

public interface CommandProvider {

    int defaultHelpPerPage();

    void updateHelpPerPage(int amount);

}