plugins {
    id("root-plugin")
}

base {
    archivesName.set("${rootProject.name.lowercase()}-${project.name}")
}

project.version = rootProject.version

dependencies {
    api(project(":api"))
}