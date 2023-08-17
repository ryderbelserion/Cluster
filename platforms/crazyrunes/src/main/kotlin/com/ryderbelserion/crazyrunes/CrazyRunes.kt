package com.ryderbelserion.crazyrunes

import com.ryderbelserion.ruby.PaperImpl
import org.bukkit.plugin.java.JavaPlugin

class CrazyRunes : JavaPlugin() {

    private lateinit var paper: PaperImpl

    override fun onEnable() {
        super.onEnable()

        this.paper = PaperImpl(this)

        this.paper.enable()

        this.paper.fileUtil().copyFiles(
            this.dataFolder.toPath().resolve("runes"),
            "runes",
            listOf(
                "fire-rune.yml",
                "lightning-rune.yml"
            )
        )
    }

    override fun onDisable() {
        super.onDisable()

        this.paper.disable()
    }
}