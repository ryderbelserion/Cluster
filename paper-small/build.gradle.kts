plugins {
    id("paper-plugin")
}

base {
    archivesName.set("${rootProject.name.lowercase()}-${project.name}-${rootProject.version}")
}

dependencies {
    api(project(":api"))
}

val component: SoftwareComponent = components["java"]

tasks {
    publishing {
        publications {
            create<MavenPublication>("maven") {
                groupId = rootProject.group.toString()
                artifactId = "${rootProject.name.lowercase()}-${project.name.lowercase()}"
                version = rootProject.version.toString()

                from(component)
            }
        }
    }

    reobfJar {
        outputJar.set(file("$buildDir/libs/${rootProject.name.lowercase()}-${project.name}-${rootProject.version}.jar"))
    }

    shadowJar {
        archiveClassifier.set("")
        mergeServiceFiles()
    }
}