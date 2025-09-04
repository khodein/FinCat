package dep.github

import dep.base.BaseDep
import dep.model.DepModel
import org.gradle.api.artifacts.VersionCatalog

object GitHubSkydovesColorPickerDep : BaseDep() {
    private const val GITHUB_SKYDOVES_COLOR_PICKER = "github-skydoves-color-picker"

    override fun invoke(libs: VersionCatalog): List<DepModel> {
        val lib = libs.findLibrary(GITHUB_SKYDOVES_COLOR_PICKER).get()
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