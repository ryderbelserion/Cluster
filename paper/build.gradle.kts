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
    paperweight.paperDevBundle(libs.versions.devBundle)

    compileOnlyApi(libs.itemsadder.api)
    compileOnlyApi(libs.oraxen.api)
    compileOnlyApi(libs.placeholder.api)
    compileOnlyApi(libs.head.database.api)

    api(libs.simple.yaml)

    api(projects.api)
}

tasks {
    assemble {
        dependsOn(reobfJar)
    }

    publishing {
        publications {
            create<MavenPublication>("maven") {
                group = project.group
                artifactId = project.name
                version = project.version.toString()

                artifact(reobfJar)
            }
        }
    }
}