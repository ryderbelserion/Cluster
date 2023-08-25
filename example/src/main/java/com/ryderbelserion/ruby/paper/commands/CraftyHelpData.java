package com.ryderbelserion.crafty.paper.commands;

import com.ryderbelserion.ruby.other.commands.CommandHelpProvider;

public class CraftyHelpData implements CommandHelpProvider {

    private final String prefix = "<gradient:#f6426e:#725bf1>[Crafty]</gradient> ";

    @Override
    public int defaultHelpPerPage() {
        return 10;
    }

    @Override
    public String optionalMessage() {
        return "{prefix}<green>This argument is optional</green>".replaceAll("\\{prefix}", prefix);
    }

    @Override
    public String requiredMessage() {
        return "{prefix}<red>This argument is not optional</red>".replaceAll("\\{prefix}", prefix);
    }

    @Override
    public String hoverMessage() {
        return "{prefix}<gray>Click me to run the command.</gray> <gold>{command}</gold>".replaceAll("\\{prefix}", prefix);
    }

    @Override
    public String hoverAction() {
        return "copy_to_clipboard";
    }

    @Override
    public String invalidFormat() {
        return "{prefix}<red>Invalid command used! Here is the correct one:</red> <blue>{command} {args}</blue>".replaceAll("\\{prefix}", prefix);
    }

    @Override
    public String notEnoughArgs() {
        return "{prefix}<red>You did not supply enough arguments.</red>".replaceAll("\\{prefix}", prefix);
    }

    @Override
    public String tooManyArgs() {
        return "{prefix}<red>You put more arguments then I can handle.</red>".replaceAll("\\{prefix}", prefix);
    }

    @Override
    public String invalidPage() {
        return "{prefix}<red>The page</red> <gold>{page}</gold> <red>does not exist.</red>".replaceAll("\\{prefix}", prefix);
    }

    @Override
    public String pageHeader() {
        return "<dark_gray>────────</dark_gray> <gold>Crafty Help {page}</gold> <dark_gray>────────</dark_gray>";
    }

    @Override
    public String pageFormat() {
        return "<gold>{command}</gold> <dark_gray>»</dark_gray> <reset>{description}";
    }

    @Override
    public String pageFooter() {
        return "<dark_gray>────────</dark_gray> <gold>Crafty Help {page}";
    }

    @Override
    public String pageNavigation() {
        return "<gray>Go to page</gray> <gold>{page}</gold>";
    }

    @Override
    public String pageNextButton() {
        return " <green>»»»</green>";
    }

    @Override
    public String pageBackButton() {
        return " <red>«««</red>";
    }

    @Override
    public String noPermission() {
        return "{prefix} You are lacking the permission <red>{permission}</red>".replaceAll("\\{prefix}", prefix);
    }

    @Override
    public String notPlayer() {
        return "{prefix} You are not a player.".replaceAll("\\{prefix}", prefix);
    }
}