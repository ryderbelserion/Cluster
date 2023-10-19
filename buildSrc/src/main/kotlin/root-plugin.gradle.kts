plugins {
    id("com.github.johnrengelman.shadow")

    `maven-publish`
    `java-library`
}

repositories {
    maven("https://repo.crazycrew.us/snapshots/")

    maven("https://repo.crazycrew.us/releases/")

    maven("https://jitpack.io")

    mavenCentral()
    mavenLocal()
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of("17"))
}

tasks {
    compileJava {
        options.encoding = Charsets.UTF_8.name()
        options.release.set(17)
    }

    shadowJar {
        archiveClassifier.set("")
        mergeServiceFiles()
    }
}

val isSnapshot = rootProject.version.toString().contains("snapshot")

publishing {
    repositories {
        maven {
            credentials {
                this.username = System.getenv("gradle_username")
                this.password = System.getenv("gradle_password")
            }

            //if (isSnapshot) {
            //    url = uri("https://repo.crazycrew.us/snapshots/")
            //    return@maven
            //}

            url = uri("https://repo.crazycrew.us/snapshots/")
        }
    }
}