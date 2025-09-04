package dep.squareup

import dep.base.BaseDep
import dep.model.DepModel
import org.gradle.api.artifacts.VersionCatalog

object SquareupLeakcanaryAndroidDep : BaseDep() {
    private const val SQUAREUP_LEAKCANARY_ANDROID = "squareup-leakcanary-android"

    override fun invoke(libs: VersionCatalog): List<DepModel> {
        val lib = libs.findLibrary(SQUAREUP_LEAKCANARY_ANDROID).get()
        val dependency = getDependency(
            module = "${lib.get().module}",
            version = "${lib.get().version}"
        )
        return DepModel(
            method = DepModel.Method.DEBUG_IMPL,
            dependency = dependency,
        ).let(::listOf)
    }
}