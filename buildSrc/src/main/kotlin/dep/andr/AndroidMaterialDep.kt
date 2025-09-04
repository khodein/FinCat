package dep.andr

import dep.base.BaseDep
import dep.model.DepModel
import org.gradle.api.artifacts.VersionCatalog

object AndroidMaterialDep : BaseDep() {
    private const val ANDROID_MATERIAL = "android-material"

    override fun invoke(libs: VersionCatalog): List<DepModel> {
        val lib = libs.findLibrary(ANDROID_MATERIAL).get()
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