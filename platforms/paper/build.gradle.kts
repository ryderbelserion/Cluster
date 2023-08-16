plugins {
    id("paper-plugin")
}

dependencies {
    implementation("net.kyori", "adventure-platform-bukkit", "4.3.0")

    api(project(":ruby-api"))
}

tasks {
    shadowJar {
        archiveClassifier.set("")
        mergeServiceFiles()
    }
}