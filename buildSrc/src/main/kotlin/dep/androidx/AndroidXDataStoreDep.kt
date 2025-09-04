package dep.androidx

import dep.base.BaseDep
import dep.model.DepModel
import org.gradle.api.artifacts.VersionCatalog

object AndroidXDataStoreDep : BaseDep() {
    private const val ANDROIDX_DATA_STORE = "androidx-data-store"

    override fun invoke(libs: VersionCatalog): List<DepModel> {
        val lib = libs.findLibrary(ANDROIDX_DATA_STORE).get()
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