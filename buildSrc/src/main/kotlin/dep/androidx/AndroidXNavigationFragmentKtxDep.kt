package dep.androidx

import dep.base.BaseDep
import dep.model.DepModel
import org.gradle.api.artifacts.VersionCatalog

object AndroidXNavigationFragmentKtxDep : BaseDep() {
    private const val ANDROIDX_NAVIGATION_FRAGMENT_KTX = "androidx-navigation-fragment-ktx"
    private const val ANDROIDX_NAVIGATION_NAVIGATION_UI_KTX =
        "androidx-navigation-navigation-ui-ktx"

    override fun invoke(libs: VersionCatalog): List<DepModel> {
        val lib1 = libs.findLibrary(ANDROIDX_NAVIGATION_FRAGMENT_KTX).get()
        val dependency1 = getDependency(
            module = "${lib1.get().module}",
            version = "${lib1.get().version}"
        )

        val lib2 = libs.findLibrary(ANDROIDX_NAVIGATION_NAVIGATION_UI_KTX).get()
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
            )
        )
    }
}