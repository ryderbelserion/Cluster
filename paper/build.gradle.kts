plugins {
    alias(libs.plugins.paperweight)
    alias(libs.plugins.shadow)
}

val mcVersion = providers.gradleProperty("mcVersion").get()

dependencies {
    api(project(":api"))

    compileOnly(libs.itemsadder)

    compileOnly(libs.arcaniax)

    compileOnly(libs.oraxen) {
        exclude("*", "*")
    }

    paperweight.paperDevBundle("$mcVersion-R0.1-SNAPSHOT")
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