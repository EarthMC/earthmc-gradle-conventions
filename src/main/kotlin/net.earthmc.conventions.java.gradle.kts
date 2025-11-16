plugins {
    id("java")
    id("com.diffplug.spotless")
}

val extension: EarthMCExtension = if (project == rootProject) {
    project.extensions.create("earthmc", EarthMCExtension::class)
} else {
    rootProject.extensions.getByType(EarthMCExtension::class)
}

project.afterEvaluate {
    java.sourceCompatibility = extension.javaVersion

    tasks {
        compileJava {
            options.encoding = Charsets.UTF_8.name()
            options.release.set(extension.javaVersion.majorVersion.toInt())
        }

        processResources {
            filteringCharset = Charsets.UTF_8.name()
        }
    }

    spotless {
        ratchetFrom("origin/${extension.mainBranch}")

        java {
            toggleOffOn()

            encoding = Charsets.UTF_8

            replaceRegex(
                "Remove blank lines before closing bracket",
                "(\\r?\\n){2,}(\\s*})",
                "${System.lineSeparator()}$2"
            )

            replaceRegex(
                "Remove multiple blank lines",
                "(\\r?\\n){3,}",
                "${System.lineSeparator()}${System.lineSeparator()}"
            )

            replaceRegex(
                "Remove assertions",
                "^\\s*assert\\b.*;[\\r\\n]?",
                ""
            )

            createLegacyColorMap().entries.forEach { entry ->
                replaceRegex(
                    "Replace legacy character ${entry.key}",
                    "\"[§&]${entry.key}(.*)\"",
                    "Component.text\\(\"$1\", NamedTextColor.${entry.value.uppercase()}\\)"
                )
            }

            leadingTabsToSpaces(4)

            removeUnusedImports()
            forbidWildcardImports()

            trimTrailingWhitespace()
            endWithNewline()
        }
    }
}

private fun createLegacyColorMap(): Map<String, String> {
    val map: MutableMap<String, String> = mutableMapOf()

    map["0"] = "black"
    map["1"] = "dark_blue"
    map["2"] = "dark_green"
    map["3"] = "dark_aqua"
    map["4"] = "dark_red"
    map["5"] = "dark_purple"
    map["6"] = "gold"
    map["7"] = "gray"
    map["8"] = "dark_gray"
    map["9"] = "blue"
    map["a"] = "green"
    map["b"] = "aqua"
    map["c"] = "red"
    map["d"] = "light_purple"
    map["e"] = "yellow"
    map["f"] = "white"

    return map
}
