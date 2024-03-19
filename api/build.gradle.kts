dependencies {
    compileOnlyApi("net.kyori", "adventure-text-minimessage", "4.16.0")
    compileOnlyApi("net.kyori", "adventure-api", "4.16.0")

    compileOnlyApi("com.github.Carleslc.Simple-YAML", "Simple-Yaml", "1.8.4")
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