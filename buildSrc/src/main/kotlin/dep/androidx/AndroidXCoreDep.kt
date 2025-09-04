package dep.androidx

import dep.base.BaseDep
import dep.model.DepModel
import org.gradle.api.artifacts.VersionCatalog

object AndroidXCoreDep : BaseDep() {
    private const val ANDROID_CORE_KTX = "androidx-core-ktx"

    override fun invoke(libs: VersionCatalog): List<DepModel> {
        val lib = libs.findLibrary(ANDROID_CORE_KTX).get()
        val dependency = getDependency(
            module = lib.get().group,
            name = lib.get().name,
            version = "${lib.get().version}"
        )
        return DepModel(
            method = DepModel.Method.IMPL,
            dependency = dependency,
        ).let(::listOf)
    }
}