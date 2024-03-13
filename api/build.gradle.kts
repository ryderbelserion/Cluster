plugins {
    id("root-plugin")
}

repositories {
    maven("https://jitpack.io/")
}

dependencies {
    compileOnlyApi(libs.simpleyaml)

    compileOnly(libs.bundles.adventure)
}

val component: SoftwareComponent = components["java"]

tasks {
    publishing {
        publications {
            create<MavenPublication>("maven") {
                groupId = rootProject.group.toString()
                artifactId = project.name.lowercase()
                version = rootProject.version.toString()

                from(component)
            }
        }
    }
}