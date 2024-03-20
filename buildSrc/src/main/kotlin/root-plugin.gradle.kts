plugins {
    `java-library`
    `maven-publish`
}

if (project != rootProject) {
    group = rootProject.group
    version = rootProject.version
    description = rootProject.description
}

repositories {
    maven("https://repo.codemc.io/repository/maven-public/")

    maven("https://repo.crazycrew.us/snapshots/")

    maven("https://jitpack.io/")

    mavenCentral()
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of("17"))
}

tasks {
    publishing {
        repositories {
            maven {
                if (project.version.toString().contains("SNAPSHOT")) {
                    url = uri("https://repo.crazycrew.us/snapshots/")
                } else {
                    url = uri("https://repo.crazycrew.us/releases/")
                }

                credentials {
                    this.username = System.getenv("GRADLE_USERNAME")
                    this.password = System.getenv("GRADLE_PASSWORD")
                }
            }
        }
    }

    compileJava {
        options.encoding = Charsets.UTF_8.name()
        options.release.set(17)
    }

    javadoc {
        options.encoding = Charsets.UTF_8.name()
    }

    processResources {
        filteringCharset = Charsets.UTF_8.name()
    }
}