package dep.coil

import dep.base.BaseDep
import dep.model.DepModel
import org.gradle.api.artifacts.VersionCatalog

object CoilDep : BaseDep() {

    private const val COIL = "coil"

    override fun invoke(libs: VersionCatalog): List<DepModel> {
        val lib = libs.findLibrary(COIL).get()
        val dependency = getDependency(
            module = "${lib.get().module}",
            version = "${lib.get().version}"
        )
        return listOf(
            DepModel(
                method = DepModel.Method.IMPL,
                dependency = dependency,
            ),
        )
    }
}