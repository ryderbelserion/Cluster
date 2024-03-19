plugins {
    `java-library`

    `maven-publish`
}

allprojects {
    apply(plugin = "java-library")
    apply(plugin = "maven-publish")

    repositories {
        maven("https://repo.extendedclip.com/content/repositories/placeholderapi/")

        maven("https://repo.papermc.io/repository/maven-public/")

        maven("https://repo.codemc.io/repository/maven-public/")

        maven("https://repo.triumphteam.dev/snapshots/")

        maven("https://repo.oraxen.com/releases/")

        maven("https://jitpack.io/")

        mavenCentral()
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