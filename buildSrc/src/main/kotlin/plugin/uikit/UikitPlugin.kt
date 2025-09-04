package plugin.uikit

import config.Config
import dep.andr.AndroidMaterialDep
import dep.androidx.AndroidXAppCompatDep
import dep.androidx.AndroidXConstraintLayoutDep
import dep.androidx.AndroidXCoreDep
import org.gradle.api.Project
import plugin.base.BasePlugin

class UikitPlugin : BasePlugin() {

    override fun apply(project: Project) {
        val libsVersionCatalog = project.getLibsVersionCatalog()

        val pluginIdList = listOf(
            libsVersionCatalog.getAndroidLibraryPluginId(),
            libsVersionCatalog.getKotlinAndroidPluginId(),
        )

        val dependencyList = listOf(
            AndroidXCoreDep.invoke(libsVersionCatalog),
            AndroidXAppCompatDep.invoke(libsVersionCatalog),
            AndroidXConstraintLayoutDep.invoke(libsVersionCatalog),
            AndroidMaterialDep.invoke(libsVersionCatalog),
        ).flatten()

        project.applyPluginIdList(pluginIdList = pluginIdList)
        project.applyDependencyList(dependencyList)

        Config.invoke(project)
    }
}