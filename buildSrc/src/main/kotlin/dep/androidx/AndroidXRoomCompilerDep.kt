package dep.androidx

import dep.base.BaseDep
import dep.model.DepModel
import org.gradle.api.artifacts.VersionCatalog

object AndroidXRoomCompilerDep : BaseDep() {
    private const val ANDROIDX_ROOM_COMPILER = "androidx-room-compiler"

    override fun invoke(libs: VersionCatalog): List<DepModel> {
        val lib = libs.findLibrary(ANDROIDX_ROOM_COMPILER).get()
        val dependency = getDependency(
            module = "${lib.get().module}",
            version = "${lib.get().version}"
        )
        return DepModel(
            method = DepModel.Method.KSP,
            dependency = dependency
        ).let(::listOf)
    }
}