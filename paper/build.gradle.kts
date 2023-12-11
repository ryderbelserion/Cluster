plugins {
    alias(libs.plugins.paperweight)
    alias(libs.plugins.shadowjar)

    `maven-publish`
}

base {
    archivesName.set("${rootProject.name.lowercase()}-${project.name}")
}

project.version = rootProject.version

dependencies {
    api(project(":common"))

    compileOnly("com.github.LoneDev6", "API-ItemsAdder", "3.5.0b")

    compileOnly("com.arcaniax", "HeadDatabase-API", "1.3.0")

    compileOnly("com.github.oraxen", "oraxen", "1.160.0") {
        exclude("*", "*")
    }

    paperweightDevelopmentBundle("io.papermc.paper:dev-bundle:1.20.4-R0.1-SNAPSHOT")
}

val component: SoftwareComponent = components["java"]

tasks {
    shadowJar {
        dependsOn(":common:shadowJar")
    }

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