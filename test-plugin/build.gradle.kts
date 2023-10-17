plugins {
    id("xyz.jpenilla.run-paper") version "2.2.0"

    id("paper-plugin")
}

dependencies {
    implementation(project(":paper"))
}

tasks {
    runServer {
        jvmArgs("-Dnet.kyori.ansi.colorLevel=truecolor")

        minecraftVersion("1.20.2")
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