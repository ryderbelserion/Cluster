plugins {
    alias(libs.plugins.paperweight)
    alias(libs.plugins.shadowjar)
}

base {
    archivesName.set("${rootProject.name.lowercase()}-${project.name}")
}

project.version = rootProject.version

dependencies {
    api(project(":common"))

    compileOnly("com.github.LoneDev6", "API-ItemsAdder", "3.5.0b")

    compileOnly("com.arcaniax", "HeadDatabase-API", "1.3.0")

    compileOnly("com.github.oraxen", "oraxen", "1.160.0") {
        exclude("*", "*")
    }

    paperweightDevelopmentBundle("io.papermc.paper:dev-bundle:1.20.4-R0.1-SNAPSHOT")
}

tasks {
    shadowJar {
        dependsOn(":common:shadowJar")
    }
}