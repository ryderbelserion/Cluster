plugins {
    id("root-plugin")
}

defaultTasks("build")

tasks {
    assemble {
        val jarsDir = File("$rootDir/jars")
        if (jarsDir.exists()) jarsDir.delete()

        subprojects.forEach { project ->
            dependsOn(":${project.name}:build")

            doLast {
                if (!jarsDir.exists()) jarsDir.mkdirs()

                if (project.name == "api") return@doLast

                val file = file("${project.layout.projectDirectory}/build/libs/${rootProject.name}-${rootProject.version}.jar")

                copy {
                    from(file)
                    into(jarsDir)
                }
            }
        }
    }
}