package com.ryderbelserion.ruby.other.commands;

public interface CommandHelpProvider {

    int defaultHelpPerPage();

    String optionalMessage();
    String requiredMessage();

    String hoverMessage();
    String hoverAction();

    String invalidFormat();
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