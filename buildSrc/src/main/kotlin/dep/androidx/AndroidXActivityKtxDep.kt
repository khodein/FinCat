package dep.androidx

import dep.base.BaseDep
import dep.model.DepModel
import org.gradle.api.artifacts.VersionCatalog

object AndroidXActivityKtxDep : BaseDep() {
    private const val ANDROIDX_ACTIVITY_KTX = "androidx-activity-ktx"

    override fun invoke(libs: VersionCatalog): List<DepModel> {
        val lib = libs.findLibrary(ANDROIDX_ACTIVITY_KTX).get()
        val dependency = getDependency(
            module = "${lib.get().module}",
            version = "${lib.get().version}"
        )
        return DepModel(
            method = DepModel.Method.IMPL,
            dependency = dependency,
        ).let(::listOf)
    }
}