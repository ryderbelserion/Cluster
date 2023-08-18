plugins {
    id("folia-plugin")
}

base {
    archivesName.set("${rootProject.name.lowercase()}-${project.name}-${rootProject.version}")
}

dependencies {
    api(project(":paper"))
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