plugins {
    `root-plugin`

    id("io.papermc.paperweight.userdev")

    alias(libs.plugins.shadow)
}

dependencies {
    paperweight.paperDevBundle(libs.versions.bundle)

    compileOnly(libs.itemsadder.api)
    compileOnly(libs.oraxen.api)
    compileOnly(libs.placeholder.api)
    compileOnly(libs.head.database.api)

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
                group = rootProject.group
                artifactId = project.name
                version = "${rootProject.version}"

                artifact(reobfJar)
            }
        }
    }
}