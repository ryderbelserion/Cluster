package com.ryderbelserion.crazyrunes

import com.ryderbelserion.ruby.other.builder.commands.args.Argument
import com.ryderbelserion.ruby.other.builder.commands.args.types.IntArgument
import com.ryderbelserion.ruby.other.builder.commands.args.types.LongArgument
import com.ryderbelserion.ruby.paper.PaperImpl
import com.ryderbelserion.ruby.paper.plugin.builder.commands.PaperCommandContext
import com.ryderbelserion.ruby.paper.plugin.builder.commands.PaperCommandEngine
import com.ryderbelserion.ruby.paper.plugin.builder.commands.args.types.PlayerArgument
import org.bukkit.command.CommandSender
import org.bukkit.plugin.java.JavaPlugin

class CrazyRunes : JavaPlugin() {

    private lateinit var paper: PaperImpl

    override fun onEnable() {
        super.onEnable()

        this.paper = PaperImpl(this)

        this.paper.enable(false)

        val manager = this.paper.manager

        manager.setNamespace("crazyrunes")

        manager.addCommand(RunesBaseCommand())
    }

    override fun onDisable() {
        super.onDisable()

        this.paper.disable()
    }
}

class RunesBaseCommand : PaperCommandEngine("players", "The base command to manager players.", "crazyrunes:players", emptyList()) {

    init {
        addCommand(RunesAddCommand())
        addCommand(RunesRemoveCommand())

        addCommand(RunesStatusCommand())
    }

    override fun perform(context: PaperCommandContext?, args: Array<out String>?) {
        context?.reply("<red>This is the base command!</red>")
    }

    override fun tabComplete(sender: CommandSender, alias: String, args: Array<out String>?): MutableList<String> {
        return handleTabComplete(args?.toList())
    }
}

class RunesAddCommand : PaperCommandEngine("add", "Add runes to a player!", "crazyrunes:players add", emptyList()) {

    init {
        this.requiredArgs.add(Argument("player", 0, PlayerArgument()))

        this.optionalArgs.add(Argument("amount", 1, IntArgument(5)))
    }

    override fun perform(context: PaperCommandContext?, args: Array<out String>?) {
        context?.reply("<green>This is the add command!</green>")
    }
}

class RunesRemoveCommand : PaperCommandEngine("remove", "Remove runes from a player!", "crazyrunes:players remove", emptyList()) {

    init {
        this.requiredArgs.add(Argument("amount", 0, IntArgument(5)))

        this.optionalArgs.add(Argument("amount", 1, LongArgument(5)))
    }

    override fun perform(context: PaperCommandContext?, args: Array<out String>?) {
        context?.reply("<gold>This is the remove command!</gold>")
    }
}

class RunesStatusCommand : PaperCommandEngine("status", "Check the status of runes on a player!", "crazyrunes:players status", emptyList()) {

    init {
        isVisible = true
    }

    override fun perform(context: PaperCommandContext?, args: Array<out String>?) {
        context?.reply("<blue>This is the status command!</blue>")
    }
}