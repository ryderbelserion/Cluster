plugins {
    `root-plugin`
}

dependencies {
    compileOnlyApi(libs.simple.yaml)

    compileOnlyApi(libs.minimessage.api)

    compileOnlyApi(libs.adventure.api)
}

val component: SoftwareComponent = components["java"]

tasks {
    publishing {
        publications {
            create<MavenPublication>("maven") {
                group = project.group
                artifactId = project.name
                version = project.version.toString()

                from(component)
            }
        }
    }
}