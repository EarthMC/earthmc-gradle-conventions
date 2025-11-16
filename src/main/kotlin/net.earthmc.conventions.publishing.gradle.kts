
plugins {
    id("net.earthmc.conventions.java")
    id("java-library")
    id("maven-publish")
    id("com.gradleup.shadow") apply false
}

val sourcesJar by tasks.registering(Jar::class) {
    archiveClassifier.set("sources")
    from(sourceSets.main.get().allSource)
}

val javadocJar by tasks.registering(Jar::class) {
    archiveClassifier.set("javadoc")
    from(tasks.named("javadoc"))
}

val extension: PublishingExtension = if (project == rootProject) {
    rootProject.extensions.getByType(EarthMCExtension::class).publishing
} else {
    project.extensions.create("earthmcPublish", PublishingExtension::class)
}

project.afterEvaluate {
    publishing {
        shadow.addShadowVariantIntoJavaComponent = false

        repositories {
            maven {
                val ext = extension
                val repository = if (project.version.toString().endsWith("-SNAPSHOT")) ext.snapshotRepository else ext.releaseRepository
                var repositoryUrl = ext.baseUrl + repository

                if (!ext.public) {
                    repositoryUrl += ext.internalPostfix
                }

                url = uri(repositoryUrl)

                name = "earthmc"
                credentials(PasswordCredentials::class)
            }
        }

        publications {
            create<MavenPublication>("maven") {
                from(components.getByName("java"))

                artifact(sourcesJar)
                artifact(javadocJar)
            }
        }
    }
}
