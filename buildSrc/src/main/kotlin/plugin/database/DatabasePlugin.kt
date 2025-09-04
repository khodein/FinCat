package plugin.database

import config.Config
import dep.androidx.AndroidXCoreDep
import dep.androidx.AndroidXRoomDep
import dep.koin.KoinAndroidDep
import org.gradle.api.Project
import plugin.base.BasePlugin

class DatabasePlugin : BasePlugin() {

    override fun apply(project: Project) {
        val libsVersionCatalog = project.getLibsVersionCatalog()

        val pluginIdList = listOf(
            libsVersionCatalog.getAndroidLibraryPluginId(),
            libsVersionCatalog.getKotlinAndroidPluginId(),
            libsVersionCatalog.getKotlinKspPluginId(),
        )

        val dependencyList = listOf(
            AndroidXCoreDep.invoke(libsVersionCatalog),
            AndroidXRoomDep.invoke(libsVersionCatalog),
            KoinAndroidDep.invoke(libsVersionCatalog),
        ).flatten()

        project.applyPluginIdList(pluginIdList = pluginIdList)
        project.applyDependencyList(dependencyList)

        Config.invoke(project)
    }
}