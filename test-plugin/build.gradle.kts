plugins {
    id("xyz.jpenilla.run-paper") version "2.2.2"

    alias(libs.plugins.paperweight)
    alias(libs.plugins.shadowjar)
}

dependencies {
    implementation(project(":paper"))

    compileOnly("io.papermc.paper:paper-api:1.20.4-R0.1-SNAPSHOT")

    //compileOnly("com.arcaniax", "HeadDatabase-API", "1.3.0")
}

tasks {
    runServer {
        jvmArgs("-Dnet.kyori.ansi.colorLevel=truecolor")

        minecraftVersion("1.20.4")
    }

    processResources {
        val props = mapOf(
            "name" to "TestPlugin",
            "group" to rootProject.group,
            "version" to rootProject.version,
            "description" to rootProject.description,
            "apiVersion" to "1.20",
        )

        filesMatching("paper-plugin.yml") {
            expand(props)
        }
    }
}