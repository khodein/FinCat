plugins {
    `kotlin-dsl`
    alias(libs.plugins.kotlin.jvm)
}

repositories {
    google()
    mavenCentral()

    gradlePlugin {
        plugins {
            register("app-plugin") {
                description = "AppPlugin"
                id = "app-plugin"
                implementationClass = "plugin.AppPlugin"
            }
        }

        plugins {
            register("core-plugin") {
                description = "CorePlugin"
                id = "core-plugin"
                implementationClass = "plugin.core.CorePlugin"
            }
        }

        plugins {
            register("feature-plugin") {
                description = "FeaturePlugin"
                id = "feature-plugin"
                implementationClass = "plugin.feature.FeaturePlugin"
            }
        }

        plugins {
            register("database-plugin") {
                description = "DatabasePlugin"
                id = "database-plugin"
                implementationClass = "plugin.database.DatabasePlugin"
            }
        }

        plugins {
            register("datastore-plugin") {
                description = "DatastorePlugin"
                id = "datastore-plugin"
                implementationClass = "plugin.datastore.DatastorePlugin"
            }
        }

        plugins {
            register("uikit-plugin") {
                description = "UikitPlugin"
                id = "uikit-plugin"
                implementationClass = "plugin.uikit.UikitPlugin"
            }
        }

        plugins {
            register("api-plugin") {
                description = "ApiPlugin"
                id = "api-plugin"
                implementationClass = "plugin.api.ApiPlugin"
            }
        }
    }
}

dependencies {
    implementation(libs.gradle)
    implementation(libs.kotlin.stdlib)
    implementation(libs.kotlin.gradle.plugin)

    implementation(gradleApi())
}