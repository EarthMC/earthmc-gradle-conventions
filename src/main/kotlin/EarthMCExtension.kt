import org.gradle.api.Action
import org.gradle.api.JavaVersion

open class EarthMCExtension {
    var javaVersion: JavaVersion = JavaVersion.VERSION_21

    val publishing: PublishingExtension = PublishingExtension()

    fun publishing(action: Action<PublishingExtension>) {
        action.execute(publishing)
    }
}

open class PublishingExtension {
    var public: Boolean = false

    var baseUrl: String = "https://repo.earthmc.net/"
    var releaseRepository: String = "releases"
    var snapshotRepository: String = "snapshots"

    var internalPostfix: String = "-internal"

    var artifactId: String? = null
    var groupId: String? = null
}
