package dep.androidx

import dep.base.BaseDep
import dep.model.DepModel
import org.gradle.api.artifacts.VersionCatalog

object AndroidXConstraintLayoutDep : BaseDep() {
    private const val ANDROIDX_CONSTRAINTLAYOUT = "androidx-constraintlayout"

    override fun invoke(libs: VersionCatalog): List<DepModel> {
        val lib = libs.findLibrary(ANDROIDX_CONSTRAINTLAYOUT).get()
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