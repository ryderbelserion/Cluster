plugins {
    `root-plugin`

    id("io.papermc.paperweight.userdev")

    alias(libs.plugins.run.paper)
    alias(libs.plugins.shadow)
}

repositories {
    maven("https://repo.extendedclip.com/content/repositories/placeholderapi/")

    maven("https://repo.papermc.io/repository/maven-public/")

    maven("https://repo.triumphteam.dev/snapshots/")

    maven("https://repo.oraxen.com/releases/")
}

dependencies {
    implementation(project(":paper"))

    paperweight.paperDevBundle(libs.versions.bundle)
}

tasks {
    runServer {
        jvmArgs("-Dnet.kyori.ansi.colorLevel=truecolor")

        defaultCharacterEncoding = Charsets.UTF_8.name()

        minecraftVersion("1.20.4")
    }
}