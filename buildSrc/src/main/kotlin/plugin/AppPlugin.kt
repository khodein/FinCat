package plugin

import config.Config
import dep.andr.AndroidMaterialDep
import dep.andr.AndroidReviewDep
import dep.andr.AndroidReviewKtxDep
import dep.androidx.AndroidXActivityKtxDep
import dep.androidx.AndroidXAppCompatDep
import dep.androidx.AndroidXConstraintLayoutDep
import dep.androidx.AndroidXCoreDep
import dep.androidx.AndroidXDataStoreDep
import dep.androidx.AndroidXNavigationFragmentKtxDep
import dep.androidx.AndroidXRoomDep
import dep.apache.ApachePoiDep
import dep.github.GitHubSkydovesColorPickerDep
import dep.koin.KoinAndroidDep
import dep.kotlinx.KotlinXCoroutineAndroidDep
import dep.kotlinx.KotlinXCoroutineCoreDep
import dep.kotlinx.KotlinXDateTimeDep
import dep.kotlinx.KotlinXSerializationJsonDep
import dep.squareup.SquareupLeakcanaryAndroidDep
import org.gradle.api.Project
import plugin.base.BasePlugin

class AppPlugin : BasePlugin() {

    override fun apply(project: Project) {
        val libsVersionCatalog = project.getLibsVersionCatalog()

        val pluginIdList = listOf(
            libsVersionCatalog.getAndroidApplicationPluginId(),
            libsVersionCatalog.getKotlinAndroidPluginId(),
            libsVersionCatalog.getKotlinKspPluginId(),
            libsVersionCatalog.getKotlinSerializationPluginId(),
        )

        project.applyPluginIdList(pluginIdList = pluginIdList)

        Config.invoke(project)

        val dependencyList = listOf(
            AndroidXCoreDep.invoke(libsVersionCatalog),
            AndroidXActivityKtxDep.invoke(libsVersionCatalog),
            AndroidXAppCompatDep.invoke(libsVersionCatalog),
            AndroidXConstraintLayoutDep.invoke(libsVersionCatalog),
            AndroidXNavigationFragmentKtxDep.invoke(libsVersionCatalog),
            KoinAndroidDep.invoke(libsVersionCatalog),
            KotlinXDateTimeDep.invoke(libsVersionCatalog),
            KotlinXCoroutineCoreDep.invoke(libsVersionCatalog),
            KotlinXCoroutineAndroidDep.invoke(libsVersionCatalog),
            KotlinXSerializationJsonDep.invoke(libsVersionCatalog),
            AndroidMaterialDep.invoke(libsVersionCatalog),
            AndroidReviewDep.invoke(libsVersionCatalog),
            AndroidReviewKtxDep.invoke(libsVersionCatalog),
            SquareupLeakcanaryAndroidDep.invoke(libsVersionCatalog),
            GitHubSkydovesColorPickerDep.invoke(libsVersionCatalog),
        ).flatten()

        project.applyDependencyList(dependencyList)
    }
}