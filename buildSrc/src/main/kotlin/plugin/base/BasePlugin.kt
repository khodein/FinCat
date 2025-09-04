package plugin.base

import dep.model.DepModel
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.getByType

abstract class BasePlugin : Plugin<Project> {

    protected fun Project.applyDependencyList(depList: List<DepModel>) {
        dependencies {
            depList.forEach { config ->
                add(
                    configurationName = config.method.value,
                    dependencyNotation = config.dependency,
                )
            }
        }
    }

    protected fun Project.applyPluginIdList(pluginIdList: List<String>) {
        pluginIdList.forEach(plugins::apply)
    }

    protected fun VersionCatalog.getKotlinAndroidPluginId(): String {
        val androidAppPlugin = this.findPlugin("kotlin-android").get()
        return androidAppPlugin.get().pluginId
    }

    protected fun VersionCatalog.getKotlinKspPluginId(): String {
        val androidAppPlugin = this.findPlugin("kotlin-ksp").get()
        return androidAppPlugin.get().pluginId
    }

    protected fun VersionCatalog.getKotlinSerializationPluginId(): String {
        val androidAppPlugin = this.findPlugin("kotlin-serialization").get()
        return androidAppPlugin.get().pluginId
    }

    protected fun VersionCatalog.getAndroidApplicationPluginId(): String {
        val androidAppPlugin = this.findPlugin("android-application").get()
        return androidAppPlugin.get().pluginId
    }

    protected fun VersionCatalog.getAndroidLibraryPluginId(): String {
        val androidAppPlugin = this.findPlugin("android-library").get()
        return androidAppPlugin.get().pluginId
    }

    protected fun Project.getLibsVersionCatalog(): VersionCatalog {
        return extensions.getByType<VersionCatalogsExtension>().named("libs")
    }
}