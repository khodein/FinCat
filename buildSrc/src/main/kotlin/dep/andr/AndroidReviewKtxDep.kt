package dep.andr

import dep.base.BaseDep
import dep.model.DepModel
import org.gradle.api.artifacts.VersionCatalog

object AndroidReviewKtxDep : BaseDep() {
    private const val ANDROID_REVIEW_KTX = "android-review-ktx"

    override fun invoke(libs: VersionCatalog): List<DepModel> {
        val lib = libs.findLibrary(ANDROID_REVIEW_KTX).get()
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