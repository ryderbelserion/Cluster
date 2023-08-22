package com.ryderbelserion.ruby.other.builder.commands.provider;

public interface LocaleProvider {

    String optionalMessage();

    String requiredMessage();

    String hoverMessage();
    String hoverAction();

    String tooManyArgs();

    String notEnoughArgs();

    String invalidPage();

    String pageHeader();
    String pageFormat();
    String pageFooter();

    String pageNavigation();
    String pageNextButton();
    String pageBackButton();

}