base {
    archivesName.set("${rootProject.name.lowercase()}-${project.name}")
}

project.version = rootProject.version

dependencies {
    api(project(":api"))
}