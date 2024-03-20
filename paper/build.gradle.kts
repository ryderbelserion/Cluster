plugins {
    `root-plugin`

    id("io.papermc.paperweight.userdev")

    alias(libs.plugins.shadow)
}

dependencies {
    paperweight.paperDevBundle(libs.versions.bundle)

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