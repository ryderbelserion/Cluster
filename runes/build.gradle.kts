plugins {
    id("paper-plugin")
}

base {
    archivesName.set("${project.name}-${rootProject.version}")
}

dependencies {
    api(project(":paper"))
}

val component: SoftwareComponent = components["java"]

tasks {
    reobfJar {
        outputJar.set(file("$buildDir/libs/${project.name}-${rootProject.version}.jar"))
    }

    shadowJar {
        archiveClassifier.set("")
        mergeServiceFiles()
    }
}