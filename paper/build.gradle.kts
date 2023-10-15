plugins {
    id("xyz.jpenilla.run-paper") version "2.1.0"

    id("paper-plugin")
}

base {
    archivesName.set("${rootProject.name.lowercase()}-${project.name}")
}

project.group = "${rootProject.group}.bukkit"

dependencies {
    api(project(":api"))
}

val component: SoftwareComponent = components["java"]

tasks {
    runServer {
        minecraftVersion("1.20.1")

        jvmArgs("-Dnet.kyori.ansi.colorLevel=truecolor")
    }

    publishing {
        publications {
            create<MavenPublication>("maven") {
                groupId = rootProject.group.toString()
                artifactId = "${rootProject.name.lowercase()}-${project.name.lowercase()}"
                version = rootProject.version.toString()

                from(component)
            }
        }
    }

    shadowJar {
        dependsOn(":api:shadowJar")
    }
}