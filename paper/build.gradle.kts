plugins {
    id("paper-plugin")
}

val mcVersion = providers.gradleProperty("mcVersion").get()

repositories {
    maven("https://repo.extendedclip.com/content/repositories/placeholderapi/")
}

dependencies {
    api(project(":api"))

    compileOnly(libs.placeholderapi)

    compileOnly(libs.itemsadder)

    compileOnly(libs.arcaniax)

    compileOnly(libs.oraxen) {
        exclude("*", "*")
    }
}

tasks {
    publishing {
        publications {
            create<MavenPublication>("maven") {
                groupId = rootProject.group.toString()
                artifactId = project.name.lowercase()
                version = rootProject.version.toString()

                artifact(reobfJar)
            }
        }
    }
}