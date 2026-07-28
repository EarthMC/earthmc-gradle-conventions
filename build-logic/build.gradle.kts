plugins {
    `kotlin-dsl`
    id("maven-publish")
    id("java-library")
}

repositories {
    gradlePluginPortal()
    mavenCentral()
}

dependencies {
    implementation(libs.spotless)
    compileOnly(libs.shadow)
}

val sourcesJar by tasks.registering(Jar::class) {
    archiveClassifier.set("sources")
    from(sourceSets.main.get().allSource)
}

val javadocJar by tasks.registering(Jar::class) {
    archiveClassifier.set("javadoc")
    from(tasks.named("javadoc"))
}

kotlin {
    jvmToolchain(21)
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
