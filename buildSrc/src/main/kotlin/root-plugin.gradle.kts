plugins {
    id("com.github.johnrengelman.shadow")

    `java-library`

    `maven-publish`
}

repositories {
    maven("https://repo.crazycrew.us/snapshots/")

    maven("https://repo.crazycrew.us/releases/")

    maven("https://jitpack.io/")

    mavenCentral()
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of("17"))
}

tasks {
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

    shadowJar {
        archiveFileName.set("${rootProject.name}-${rootProject.version}.jar")

        archiveClassifier.set("")

        exclude("META-INF/**")
    }

    publishing {
        repositories {
            maven {
                credentials {
                    this.username = System.getenv("GRADLE_USERNAME")
                    this.password = System.getenv("GRADLE_PASSWORD")
                }

                url = uri("https://repo.crazycrew.us/snapshots/")
            }
        }
    }
}