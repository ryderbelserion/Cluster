package com.ryderbelserion.runes

import com.ryderbelserion.ruby.PaperImpl
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.plugin.java.JavaPlugin

class Runes : JavaPlugin() {

    private lateinit var paper: PaperImpl

    override fun onEnable() {
        super.onEnable()

        this.paper = PaperImpl(this)

        this.paper.enable()
    }

    override fun onDisable() {
        super.onDisable()

        this.paper.disable()
    }
}