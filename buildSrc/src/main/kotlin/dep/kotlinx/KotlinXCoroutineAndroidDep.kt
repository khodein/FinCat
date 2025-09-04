package dep.kotlinx

import dep.base.BaseDep
import dep.model.DepModel
import org.gradle.api.artifacts.VersionCatalog

object KotlinXCoroutineAndroidDep : BaseDep() {
    private const val KOTLINX_COROUTINES_ANDROID = "kotlinx-coroutines-android"

    override fun invoke(libs: VersionCatalog): List<DepModel> {
        val lib = libs.findLibrary(KOTLINX_COROUTINES_ANDROID).get()
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