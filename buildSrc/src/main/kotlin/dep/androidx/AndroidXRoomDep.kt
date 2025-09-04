package dep.androidx

import dep.base.BaseDep
import dep.model.DepModel
import org.gradle.api.artifacts.VersionCatalog

object AndroidXRoomDep : BaseDep() {

    override fun invoke(libs: VersionCatalog): List<DepModel> {
        return listOf(
            AndroidXRoomKtxDep.invoke(libs),
            AndroidXRoomRuntimeDep.invoke(libs),
            AndroidXRoomCompilerDep.invoke(libs),
        ).flatten()
    }
}