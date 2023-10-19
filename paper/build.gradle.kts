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

    compileOnly("com.github.oraxen", "oraxen", "1.160.0") {
        exclude("*", "*")
    }
}

tasks {
    publishing {
        publications {
            create<MavenPublication>("maven") {
                groupId = rootProject.group.toString()
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