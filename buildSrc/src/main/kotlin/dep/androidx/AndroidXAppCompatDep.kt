package dep.androidx

import dep.base.BaseDep
import dep.model.DepModel
import org.gradle.api.artifacts.VersionCatalog

object AndroidXAppCompatDep : BaseDep() {
    private const val ANDROIDX_APPCOMPAT = "androidx-appcompat"

    override fun invoke(libs: VersionCatalog): List<DepModel> {
        val lib = libs.findLibrary(ANDROIDX_APPCOMPAT).get()
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