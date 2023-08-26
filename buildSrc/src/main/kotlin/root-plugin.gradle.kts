plugins {
    id("com.github.johnrengelman.shadow")

    `java-library`
}

repositories {
    maven("https://repo.crazycrew.us/snapshots/")

    maven("https://repo.crazycrew.us/releases/")

    maven("https://jitpack.io")

    mavenCentral()
}