package com.ryderbelserion.crazyrunes

import com.ryderbelserion.ruby.PaperImpl
import org.bukkit.plugin.java.JavaPlugin

class CrazyRunes : JavaPlugin() {

    private lateinit var paper: PaperImpl

    override fun onEnable() {
        super.onEnable()

        this.paper = PaperImpl(this)
    }

    override fun onDisable() {
        super.onDisable()
    }
}