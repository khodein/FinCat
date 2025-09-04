package dep.koin

import dep.base.BaseDep
import dep.model.DepModel
import org.gradle.api.artifacts.VersionCatalog

object KoinAndroidDep : BaseDep() {
    private const val KOIN_ANDROID = "koin-android"
    private const val KOIN_CORE = "koin-core"

    override fun invoke(libs: VersionCatalog): List<DepModel> {
        val lib1 = libs.findLibrary(KOIN_ANDROID).get()
        val dependency1 = getDependency(
            module = "${lib1.get().module}",
            version = "${lib1.get().version}"
        )
        val lib2 = libs.findLibrary(KOIN_CORE).get()
        val dependency2 = getDependency(
            module = "${lib2.get().module}",
            version = "${lib2.get().version}"
        )
        return listOf(
            DepModel(
                method = DepModel.Method.IMPL,
                dependency = dependency1,
            ),
            DepModel(
                method = DepModel.Method.IMPL,
                dependency = dependency2,
            ),
        )
    }
}