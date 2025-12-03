# earthmc-gradle-conventions

<a rel="noreferrer" target="_blank" href="https://repo.earthmc.net/#/releases/net/earthmc/conventions/earthmc-gradle-conventions">
    <img src="https://repo.earthmc.net/api/badge/latest/releases/net/earthmc/conventions/earthmc-gradle-conventions?color=40c14a&name=earthmc-gradle-conventions" alt="Latest version badge">
</a>

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
plugins {
    id("net.earthmc.conventions.java") version "<VERSION>"
}
```
