package dep.kotlinx

import dep.base.BaseDep
import dep.model.DepModel
import org.gradle.api.artifacts.VersionCatalog

object KotlinXDateTimeDep : BaseDep() {

    private const val KOTLINX_DATETIME = "kotlinx-datetime"

    override fun invoke(libs: VersionCatalog): List<DepModel> {
        val lib = libs.findLibrary(KOTLINX_DATETIME).get()
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