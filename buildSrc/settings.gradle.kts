dependencyResolutionManagement {
    repositories {
        maven("https://repo.papermc.io/repository/maven-public/")

        gradlePluginPortal()
        mavenCentral()
    }

    versionCatalogs {
        create("libs") {
            library("paperweight", "io.papermc.paperweight", "paperweight-userdev").version("1.5.11")

            library("shadow", "com.github.johnrengelman", "shadow").version("8.1.1")

            library("runpaper", "xyz.jpenilla", "run-task").version("2.2.3")
        }
    }
}