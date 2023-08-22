package com.ryderbelserion.ruby.other.builder.commands;

public interface CommandHelpProvider {

    int defaultHelpPerPage();

    void updateHelpPerPage(int amount);

    String optionalMessage();
    String requiredMessage();

    String hoverMessage();
    String hoverAction();

    String notEnoughArgs();
    String tooManyArgs();

    String invalidPage();

    String pageHeader();
    String pageFormat();
    String pageFooter();

    String pageNavigation();
    String pageNextButton();
    String pageBackButton();

    String noPermission();
    String notPlayer();

}