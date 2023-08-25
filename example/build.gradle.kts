plugins {
    id("xyz.jpenilla.run-paper") version "2.1.0"

    id("paper-plugin")
}

project.group = "${rootProject.group}.paper"

dependencies {
    api(project(":paper"))
}

tasks {
    runServer {
        minecraftVersion("1.20.1")

        jvmArgs("-Dnet.kyori.ansi.colorLevel=truecolor")
    }

    processResources {
        filesMatching("paper-plugin.yml") {
            val props = mapOf(
                "name" to rootProject.name,
                "group" to project.group.toString(),
                "version" to rootProject.version,
                "description" to rootProject.description,
                "authors" to rootProject.properties["authors"],
                "apiVersion" to "1.20",
                "website" to "https://modrinth.com/plugin/${rootProject.name.lowercase()}"
            )

            expand(props)
        }
    }
}