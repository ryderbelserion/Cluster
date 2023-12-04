plugins {
    id("paper-plugin")
}

base {
    archivesName.set("${rootProject.name.lowercase()}-${project.name}")
}

project.group = "${rootProject.group}.paper"

dependencies {
    api(project(":api"))

    compileOnly("com.github.LoneDev6", "API-ItemsAdder", "3.5.0b")

    compileOnly("com.arcaniax", "HeadDatabase-API", "1.3.0")

    compileOnly("com.github.oraxen", "oraxen", "1.160.0") {
        exclude("*", "*")
    }
}

tasks {
    publishing {
        publications {
            create<MavenPublication>("maven") {
                groupId = project.group.toString()
                artifactId = "${rootProject.name.lowercase()}-${project.name.lowercase()}"
                version = rootProject.version.toString()

                artifact(reobfJar)
            }
        }
    }

    shadowJar {
        dependsOn(":api:shadowJar")
    }
}