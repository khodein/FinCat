package plugin.datastore

import config.Config
import dep.androidx.AndroidXCoreDep
import dep.androidx.AndroidXDataStoreDep
import dep.koin.KoinAndroidDep
import org.gradle.api.Project
import plugin.base.BasePlugin

class DatastorePlugin : BasePlugin() {

    override fun apply(project: Project) {
        val libsVersionCatalog = project.getLibsVersionCatalog()

        val pluginIdList = listOf(
            libsVersionCatalog.getAndroidLibraryPluginId(),
            libsVersionCatalog.getKotlinAndroidPluginId(),
        )

        val dependencyList = listOf(
            AndroidXCoreDep.invoke(libsVersionCatalog),
            AndroidXDataStoreDep.invoke(libsVersionCatalog),
            KoinAndroidDep.invoke(libsVersionCatalog),
        ).flatten()

        project.applyPluginIdList(pluginIdList = pluginIdList)
        project.applyDependencyList(dependencyList)

        Config.invoke(project)
    }
}