pluginManagement {
    repositories {
        maven("https://repo.papermc.io/repository/maven-public/")

        gradlePluginPortal()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositories {
        maven("https://repo.papermc.io/repository/maven-public/")

        gradlePluginPortal()
        mavenCentral()
    }

    versionCatalogs {
        create("libs") {
            version("adventure4", "4.16.0")

            library("adventure4", "net.kyori", "adventure-text-minimessage").versionRef("adventure4")
            library("minimessage4", "net.kyori", "adventure-api").versionRef("adventure4")

            library("simpleyaml", "com.github.Carleslc.Simple-YAML", "Simple-Yaml").version("1.8.4")

            library("itemsadder", "com.github.LoneDev6", "api-itemsadder").version("3.6.1")
            library("oraxen", "io.th0rgal", "oraxen").version("1.164.0")

            library("placeholderapi", "me.clip", "placeholderapi").version("2.11.5")
            library("arcaniax", "com.arcaniax", "HeadDatabase-API").version("1.3.0")

            plugin("paperweight", "io.papermc.paperweight.userdev").version("1.5.9")
            plugin("shadow", "com.github.johnrengelman.shadow").version("8.1.1")

            bundle("adventure", listOf("adventure4", "minimessage4"))
        }
    }
}

rootProject.name = "Cluster"

include("paper", "api", "dev-plugin")