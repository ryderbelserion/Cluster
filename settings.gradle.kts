pluginManagement {
    repositories {
        maven("https://repo.papermc.io/repository/maven-public/")

        gradlePluginPortal()
    }
}

rootProject.name = "Ruby"

val lowerCase = rootProject.name.lowercase()

listOf(
    "api"
).forEach(::includeProject)

listOf(
    "paper",

    "runes"
).forEach(::includePlatform)

include("platforms")

fun includeProject(name: String) {
    include(name) {
        this.name = name
    }
}

fun includePlatform(name: String) {
    include(name) {
        this.name = name
        this.projectDir = file("platforms/$name")
    }
}

fun include(name: String, block: ProjectDescriptor.() -> Unit) {
    include(name)
    project(":$name").apply(block)
}