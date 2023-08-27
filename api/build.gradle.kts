plugins {
    id("root-plugin")
}

base {
    archivesName.set("${rootProject.name.lowercase()}-${project.name}")
}

project.version = "1.0"
project.group = "${rootProject.group}.api"

repositories {
    maven("https://libraries.minecraft.net/")
}

dependencies {
    compileOnly("net.kyori", "adventure-text-minimessage", "4.14.0")

    compileOnly("com.google.code.gson", "gson", "2.10.1")

    compileOnly("net.kyori", "adventure-api", "4.14.0")
}

val component: SoftwareComponent = components["java"]

tasks {
    publishing {
        publications {
            create<MavenPublication>("maven") {
                groupId = project.group.toString()
                artifactId = "${rootProject.name.lowercase()}-${project.name.lowercase()}"
                version = project.version.toString()

                from(component)
            }
        }
    }
}