package com.ryderbelserion.crazyrunes

import com.google.gson.GsonBuilder
import com.ryderbelserion.ruby.PaperImpl
import com.ryderbelserion.ruby.other.config.FileEngine
import com.ryderbelserion.ruby.other.config.FileManager
import com.ryderbelserion.ruby.other.config.types.FileType
import org.bukkit.plugin.java.JavaPlugin
import java.lang.reflect.Modifier

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

        val files = FileManager()

        val locations = Locations(this)

        files.addFile(locations)
    }

    override fun onDisable() {
        super.onDisable()

        this.paper.disable()
    }

    fun paper(): PaperImpl {
        return this.paper
    }
}

class Locations(private val plugin: CrazyRunes) : FileEngine("locations.json", plugin.dataFolder.toPath(), FileType.JSON) {

    init {
        setGsonBuilder(GsonBuilder().disableHtmlEscaping()
            .excludeFieldsWithModifiers(Modifier.TRANSIENT)
            .excludeFieldsWithoutExposeAnnotation())
    }

    override fun load() {
        this.plugin.paper().logger().info("A second load")
    }

    override fun save() {
        this.plugin.paper().logger().info("A second save")
    }
}