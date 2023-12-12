plugins {
    alias(libs.plugins.paperweight)
    alias(libs.plugins.shadowjar)
}

val mcVersion = rootProject.properties["minecraftVersion"] as String

dependencies {
    api(project(":api"))

    compileOnly("com.github.LoneDev6", "API-ItemsAdder", "3.5.0b")

    compileOnly("com.arcaniax", "HeadDatabase-API", "1.3.0")

    compileOnly("com.github.oraxen", "oraxen", "1.160.0") {
        exclude("*", "*")
    }

    paperweightDevelopmentBundle("io.papermc.paper:dev-bundle:$mcVersion-R0.1-SNAPSHOT")
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