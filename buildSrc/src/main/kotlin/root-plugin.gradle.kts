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

tasks {
    publishing {
        publications {
            create<MavenPublication>("maven") {
                from(components["java"])

                pom {
                    name.set("Cluster API")
                    description.set("A library for my plugins.")
                    url.set("https://github.com/ryderbelserion/Cluster")

                    licenses {
                        license {
                            name.set("MIT")
                            url.set("https://github.com/ryderbelserion/Cluster/blob/main/LICENSE")
                        }
                    }

                    developers {
                        developer {
                            id.set("ryderbelserion")
                            name.set("Ryder Belserion")
                            url.set("https://github.com/ryderbelserion")
                            email.set("no-reply@ryderbelserion.com")
                        }
                    }

                    scm {
                        connection.set("scm:git:https://github.com/ryderbelserion/Cluster.git")
                        developerConnection.set("scm:git:git@github.com:ryderbelserion/Cluster.git")
                        url.set("https://github.com/ryderbelserion/Cluster")
                    }

                    issueManagement {
                        system.set("GitHub")
                        url.set("https://github.com/ryderbelserion/Cluster/issues")
                    }
                }
            }
        }

        repositories {
            maven {
                credentials {
                    this.username = System.getenv("gradle_username")
                    this.password = System.getenv("gradle_password")
                }

                url = uri("https://repo.crazycrew.us/snapshots/")
            }
        }
    }
}