plugins {
    id("io.papermc.paperweight.userdev")

    id("root-plugin")
}

repositories {
    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    paperweight.foliaDevBundle("1.20.1-R0.1-SNAPSHOT")
}

tasks {
    assemble {
        dependsOn(reobfJar)
    }
}