package com.ryderbelserion.crazyrunes

import com.ryderbelserion.ruby.paper.PaperImpl
import com.ryderbelserion.ruby.paper.plugin.builder.commands.PaperCommandContext
import com.ryderbelserion.ruby.paper.plugin.builder.commands.PaperCommandEngine
import org.bukkit.command.CommandSender
import org.bukkit.plugin.java.JavaPlugin

class CrazyRunes : JavaPlugin() {

    private lateinit var paper: PaperImpl

    override fun onEnable() {
        super.onEnable()

        this.paper = PaperImpl(this)

        this.paper.enable()

        val manager = this.paper.manager

        manager.setNamespace("crazyrunes")

        manager.addCommand(RuneBaseCommand())
        manager.addCommand(RuneAddCommand())
    }

    override fun onDisable() {
        super.onDisable()

        this.paper.disable()
    }
}

class RuneBaseCommand : PaperCommandEngine("runes", "The base rune command.", "crazyrunes:runes", emptyList()) {

    init {
        addCommand(RuneAddCommand())
        addCommand(RuneRemoveCommand())

        addCommand(RunesStatusCommand())
    }

    override fun perform(context: PaperCommandContext?, args: Array<out String>?) {
        context?.reply("<red>This is the base command!</red>")
    }

    override fun tabComplete(sender: CommandSender, alias: String, args: Array<out String>?): MutableList<String> {
        return handleTabComplete(args?.toList())
    }
}

class RuneAddCommand : PaperCommandEngine("add", "Add runes to a player!", "crazyrunes:runes add", emptyList()) {

    override fun perform(context: PaperCommandContext?, args: Array<out String>?) {
        context?.reply("<green>This is the add command!</green>")
    }
}

class RuneRemoveCommand : PaperCommandEngine("remove", "Remove runes from a player!", "crazyrunes:runes remove", emptyList()) {

    override fun perform(context: PaperCommandContext?, args: Array<out String>?) {
        context?.reply("<gold>This is the remove command!</gold>")
    }
}

class RunesStatusCommand : PaperCommandEngine("status", "Check the status of runes on a player!", "crazyrunes:runes status", emptyList()) {

    init {
        isVisible = false
    }

    override fun perform(context: PaperCommandContext?, args: Array<out String>?) {
        context?.reply("<blue>This is the status command!</blue>")
    }
}