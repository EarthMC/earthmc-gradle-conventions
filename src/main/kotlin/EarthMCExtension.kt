import org.gradle.api.Action
import org.gradle.api.JavaVersion

open class EarthMCExtension {
    var mainBranch: String? = "main"
    var javaVersion: JavaVersion = JavaVersion.VERSION_21

    val publishing: EarthMCPublishingExtension = EarthMCPublishingExtension()

    fun publishing(action: Action<EarthMCPublishingExtension>) {
        action.execute(publishing)
    }
}

open class EarthMCPublishingExtension {
    var public: Boolean = false

    var repositoryUrl: String = "https://repo.earthmc.net/"
    var repositoryName: String = "earthmc"

    var releaseRepository: String = "releases"
    var snapshotRepository: String = "snapshots"

    var internalPostfix: String = "-internal"

    var artifactId: String? = null
    var groupId: String? = null

    var sources: Boolean = true
    var javadoc: Boolean = true
}
