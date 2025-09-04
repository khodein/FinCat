package dep.andr

import dep.base.BaseDep
import dep.model.DepModel
import org.gradle.api.artifacts.VersionCatalog

object AndroidReviewDep : BaseDep() {
    private const val ANDROID_REVIEW = "android-review"

    override fun invoke(libs: VersionCatalog): List<DepModel> {
        val lib = libs.findLibrary(ANDROID_REVIEW).get()
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