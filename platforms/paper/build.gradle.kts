plugins {
    id("paper-plugin")
}

base {
    archivesName.set("${rootProject.name.lowercase()}-${project.name}-${rootProject.version}")
}

dependencies {
    api("net.kyori", "adventure-platform-bukkit", "4.3.0")

    api(project(":api"))
}

tasks {
    reobfJar {
        outputJar.set(file("$buildDir/libs/${rootProject.name.lowercase()}-${project.name}-${rootProject.version}.jar"))
    }

    shadowJar {
        archiveClassifier.set("")
        mergeServiceFiles()
    }
}