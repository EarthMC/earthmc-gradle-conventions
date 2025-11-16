# earthmc-gradle-conventions

`settings.gradle.kts`
```kotlin
pluginManagement {
    repositories {
        gradlePluginPortal()
        maven("https://repo.earthmc.net/public")
    }
}
```

`build.gradle.kts`
```kotlin
id("net.earthmc.conventions.java") version "1.0.1"
```
