package dep.apache

import dep.base.BaseDep
import dep.model.DepModel
import org.gradle.api.artifacts.VersionCatalog

object ApachePoiDep : BaseDep() {
    private const val APACHE_POI = "apache-poi"
    private const val APACHE_POI_OOXML = "apache-poi-ooxml"

    override fun invoke(libs: VersionCatalog): List<DepModel> {
        val lib1 = libs.findLibrary(APACHE_POI).get()
        val dependency1 = getDependency(
            module = "${lib1.get().module}",
            version = "${lib1.get().version}"
        )
        val lib2 = libs.findLibrary(APACHE_POI_OOXML).get()
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