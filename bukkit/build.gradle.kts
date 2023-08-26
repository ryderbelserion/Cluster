plugins {
    id("paper-plugin")
}

repositories {
    maven("https://libraries.minecraft.net/")
}

dependencies {
    implementation("me.lucko", "commodore", "2.2")
}

tasks {
    shadowJar {
        archiveFileName.set("${rootProject.name}-$version.jar")

        dependencies {
            exclude("com.mojang:brigadier")
        }
    }
}