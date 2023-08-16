plugins {
    id("xyz.jpenilla.run-paper") version "2.1.0"

    id("kotlin-plugin")
    id("paper-plugin")
}

base {
    archivesName.set("${project.name}-${project.version}")
}

project.group = "com.ryderbelserion.runes"
project.version = "1.0.0"
project.description = "A runes plugin."

dependencies {
    api(project(":paper"))
}

tasks {
    runServer {
        minecraftVersion("1.20.1")

        jvmArgs("-Dnet.kyori.ansi.colorLevel=truecolor")
    }

    reobfJar {
        outputJar.set(file("$buildDir/libs/${project.name}-${project.version}.jar"))
    }
}