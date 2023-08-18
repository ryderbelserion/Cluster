pluginManagement {
    repositories {
        maven("https://repo.papermc.io/repository/maven-public/")

        gradlePluginPortal()
    }
}

rootProject.name = "Ruby"

listOf(
    "api",
    "folia",
    "paper"
).forEach {
    include(it)
}