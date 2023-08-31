plugins {
    id("xyz.jpenilla.run-paper") version "2.1.0"

    id("paper-plugin")
}

base {
    archivesName.set("${rootProject.name.lowercase()}-${project.name}")
}

project.version = "1.0"
project.group = "${rootProject.group}.bukkit"

repositories {
    maven("https://libraries.minecraft.net/")
}

dependencies {
    api(project(":api"))

    compileOnly("com.mojang", "brigadier", "1.0.18")
}

val component: SoftwareComponent = components["java"]

tasks {
    publishing {
        publications {
            create<MavenPublication>("maven") {
                groupId = project.group.toString()
                artifactId = "${rootProject.name.lowercase()}-${project.name.lowercase()}"
                version = project.version.toString()

                from(component)
            }
        }
    }

    runServer {
        minecraftVersion("1.20.1")

        jvmArgs("-Dnet.kyori.ansi.colorLevel=truecolor -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005")
    }
}