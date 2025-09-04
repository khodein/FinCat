package plugin.core

import config.Config
import dep.androidx.AndroidXCoreDep
import org.gradle.api.Project
import plugin.base.BasePlugin

class CorePlugin : BasePlugin() {

    override fun apply(project: Project) {
        val libsVersionCatalog = project.getLibsVersionCatalog()

        val pluginIdList = listOf(
            libsVersionCatalog.getAndroidLibraryPluginId(),
            libsVersionCatalog.getKotlinAndroidPluginId(),
        )

        val dependencyList = listOf(
            AndroidXCoreDep.invoke(libsVersionCatalog),
        ).flatten()

        project.applyPluginIdList(pluginIdList = pluginIdList)
        project.applyDependencyList(dependencyList)

        Config.invoke(project)
    }
}