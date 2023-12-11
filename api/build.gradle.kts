plugins {
    `maven-publish`
}

base {
    archivesName.set("${rootProject.name.lowercase()}-${project.name}")
}

project.version = rootProject.version

dependencies {
    compileOnly("net.kyori", "adventure-text-minimessage", "4.14.0")

    compileOnly("com.google.code.gson", "gson", "2.10.1")

    compileOnly("net.kyori", "adventure-api", "4.14.0")
}

val component: SoftwareComponent = components["java"]

tasks {
    publishing {
        publications {
            create<MavenPublication>("maven") {
                from(component)

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
    }
}