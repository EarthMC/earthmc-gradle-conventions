plugins {
    `kotlin-dsl`
    id("maven-publish")
}

repositories {
    gradlePluginPortal()
    mavenCentral()
}

dependencies {
    implementation(libs.spotless)
    implementation(libs.shadow)
}

val sourcesJar by tasks.registering(Jar::class) {
    archiveClassifier.set("sources")
    from(sourceSets.main.get().allSource)
}

val javadocJar by tasks.registering(Jar::class) {
    archiveClassifier.set("javadoc")
    from(tasks.named("javadoc"))
}

publishing {
    repositories {
        maven {
            name = "earthmc"
            url = uri("https://repo.earthmc.net/${if (project.version.toString().endsWith("-SNAPSHOT")) "snapshots" else "releases"}")
            credentials(PasswordCredentials::class)
        }
    }

    publications {
        withType<MavenPublication> {
            artifact(sourcesJar)
            artifact(javadocJar)
        }
    }
}
