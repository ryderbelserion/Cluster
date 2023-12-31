plugins {
    `java-library`
    `maven-publish`
}

subprojects {
    apply(plugin = "java-library")
    apply(plugin = "maven-publish")

    repositories {
        maven("https://repo.crazycrew.us/snapshots/")

        maven("https://repo.crazycrew.us/releases/")

        maven("https://jitpack.io/")

        mavenCentral()
    }

    if (name == "paper") {
        repositories {
            maven("https://repo.extendedclip.com/content/repositories/placeholderapi/")

            maven("https://repo.codemc.io/repository/maven-public/")

            maven("https://repo.triumphteam.dev/snapshots/")

            maven("https://repo.oraxen.com/releases/")

            flatDir { dirs("libs") }
        }
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

    java {
        toolchain.languageVersion.set(JavaLanguageVersion.of("17"))
    }
}