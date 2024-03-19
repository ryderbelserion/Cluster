plugins {
    id("io.papermc.paperweight.userdev") version "1.5.11"
}

val mcVersion = providers.gradleProperty("mcVersion").get()

dependencies {
    implementation(project(":api"))

    api("com.github.Carleslc.Simple-YAML", "Simple-Yaml", "1.8.4")

    compileOnlyApi("com.github.LoneDev6", "api-itemsadder", "3.6.1")
    compileOnlyApi("com.arcaniax", "HeadDatabase-API", "1.3.0")
    compileOnlyApi("me.clip", "placeholderapi", "2.11.5")
    compileOnlyApi("io.th0rgal", "oraxen", "1.164.0")

    paperweight.paperDevBundle("$mcVersion-R0.1-SNAPSHOT")
}

tasks {
    assemble {
        dependsOn(reobfJar)
    }

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