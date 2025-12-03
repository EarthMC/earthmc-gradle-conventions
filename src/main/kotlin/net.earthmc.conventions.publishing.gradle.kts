import com.github.jengelman.gradle.plugins.shadow.ShadowExtension

plugins {
    id("net.earthmc.conventions.java")
    id("java-library")
    id("maven-publish")
}

val sourcesJar by tasks.registering(Jar::class) {
    archiveClassifier.set("sources")
    from(sourceSets.main.get().allSource)
}

val javadocJar by tasks.registering(Jar::class) {
    archiveClassifier.set("javadoc")
    from(tasks.named("javadoc"))
}

val extension: PublishingExtension = if (project == rootProject || project.extensions.findByType(EarthMCExtension::class) != null) {
    project.extensions.getByType(EarthMCExtension::class).publishing
} else {
    project.extensions.create("earthmcPublish", PublishingExtension::class)
}

project.pluginManager.withPlugin("com.gradleup.shadow") {
    extensions.configure<ShadowExtension>("shadow") {
        addShadowVariantIntoJavaComponent = false
    }
}

project.afterEvaluate {
    publishing {
        val ext = extension

        repositories {
            maven {
                val repository = if (project.version.toString().endsWith("-SNAPSHOT")) ext.snapshotRepository else ext.releaseRepository
                var repositoryUrl = ext.repositoryUrl + (if (!ext.repositoryUrl.endsWith('/')) "/" else "") + repository

                if (!ext.public) {
                    repositoryUrl += ext.internalPostfix
                }

                url = uri(repositoryUrl)

                name = ext.repositoryName
                credentials(PasswordCredentials::class)
            }
        }

        publications {
            create<MavenPublication>("maven") {
                from(components.getByName("java"))

                artifact(sourcesJar)
                artifact(javadocJar)

                ext.artifactId.let { id -> artifactId = id }
                ext.groupId.let { group -> groupId = group }
            }
        }
    }
}
