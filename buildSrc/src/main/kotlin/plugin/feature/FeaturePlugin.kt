package plugin.feature

import config.Config
import dep.andr.AndroidMaterialDep
import dep.andr.AndroidReviewDep
import dep.andr.AndroidReviewKtxDep
import dep.androidx.AndroidXActivityKtxDep
import dep.androidx.AndroidXAppCompatDep
import dep.androidx.AndroidXConstraintLayoutDep
import dep.androidx.AndroidXCoreDep
import dep.androidx.AndroidXNavigationFragmentKtxDep
import dep.apache.ApachePoiDep
import dep.github.GitHubSkydovesColorPickerDep
import dep.koin.KoinAndroidDep
import dep.kotlinx.KotlinXCoroutineAndroidDep
import dep.kotlinx.KotlinXCoroutineCoreDep
import dep.kotlinx.KotlinXDateTimeDep
import dep.kotlinx.KotlinXSerializationJsonDep
import org.gradle.api.Project
import plugin.base.BasePlugin

class FeaturePlugin : BasePlugin() {

    override fun apply(project: Project) {
        val libsVersionCatalog = project.getLibsVersionCatalog()
        val pluginIdList = listOf(
            libsVersionCatalog.getAndroidLibraryPluginId(),
            libsVersionCatalog.getKotlinAndroidPluginId(),
            libsVersionCatalog.getKotlinKspPluginId(),
            libsVersionCatalog.getKotlinSerializationPluginId(),
        )

        val dependencyList = listOf(
            AndroidXCoreDep.invoke(libsVersionCatalog),
            AndroidXAppCompatDep.invoke(libsVersionCatalog),
            AndroidXConstraintLayoutDep.invoke(libsVersionCatalog),
            KoinAndroidDep.invoke(libsVersionCatalog),
            KotlinXDateTimeDep.invoke(libsVersionCatalog),
            KotlinXCoroutineCoreDep.invoke(libsVersionCatalog),
            KotlinXCoroutineAndroidDep.invoke(libsVersionCatalog),
            KotlinXSerializationJsonDep.invoke(libsVersionCatalog),
            AndroidMaterialDep.invoke(libsVersionCatalog),
        ).flatten()

        project.applyPluginIdList(pluginIdList = pluginIdList)
        project.applyDependencyList(dependencyList)

        Config.invoke(project)
    }
}