package dep.kotlinx

import dep.base.BaseDep
import dep.model.DepModel
import org.gradle.api.artifacts.VersionCatalog

object KotlinXSerializationJsonDep : BaseDep() {
    private const val KOTLINX_SERIALIZATION_JSON = "kotlinx-serialization-json"

    override fun invoke(libs: VersionCatalog): List<DepModel> {
        val lib = libs.findLibrary(KOTLINX_SERIALIZATION_JSON).get()
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