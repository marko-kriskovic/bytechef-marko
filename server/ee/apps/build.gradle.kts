plugins {
    id("com.bytechef.java-application-conventions") apply false
    alias(libs.plugins.com.google.cloud.tools.jib) apply false
    alias(libs.plugins.com.gorylenko.gradle.git.properties) apply false
    alias(libs.plugins.org.springframework.boot) apply false
}

subprojects {
    apply(plugin = "com.bytechef.java-application-conventions")
    apply(plugin = "com.google.cloud.tools.jib")
    apply(plugin = "com.gorylenko.gradle-git-properties")
    apply(plugin = "org.springframework.boot")

    if (project.hasProperty("prod")) {
        apply(from = rootDir.absolutePath + "/server/gradle/profile_prod.gradle")
    } else {
        apply(from = rootDir.absolutePath + "/server/gradle/profile_dev.gradle")
    }

    if (project.hasProperty("native")) {
        apply(from = rootDir.absolutePath + "/server/gradle/profile_native.gradle")
    }

    defaultTasks("bootRun")

    configure<com.gorylenko.GitPropertiesPluginExtension> {
        failOnNoGitDirectory = false
        setKeys(listOf("git.branch", "git.build.version", "git.commit.id", "git.commit.id.abbrev", "git.commit.id.describe"))
    }

    configure<com.google.cloud.tools.jib.gradle.JibExtension> {
        from {
            image = "ghcr.io/graalvm/graalvm-community:20.0.2-ol9"
        }
        to {
            image = "bytechef/bytechef-" + project.name + ":latest"
        }
    }
}