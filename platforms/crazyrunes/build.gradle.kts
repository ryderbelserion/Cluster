plugins {
    id("xyz.jpenilla.run-paper") version "2.1.0"

    id("kotlin-plugin")
    id("paper-plugin")
}

dependencies {
    api(project(":ruby-paper"))
}

tasks {
    runServer {
        minecraftVersion("1.20.1")

        jvmArgs("-Dnet.kyori.ansi.colorLevel=truecolor")
    }
}