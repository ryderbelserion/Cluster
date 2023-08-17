plugins {
    id("root-plugin")
}

base {
    archivesName.set("${rootProject.name.lowercase()}-${project.name}-${rootProject.version}")
}

dependencies {
    compileOnly("net.kyori", "adventure-text-minimessage", "4.14.0")

    compileOnly("net.kyori", "adventure-platform-api","4.3.0")

    compileOnly("com.google.code.gson", "gson", "2.10.1")

    compileOnly("net.kyori", "adventure-api", "4.14.0")
}

tasks {
    shadowJar {
        archiveClassifier.set("")
        mergeServiceFiles()
    }
}