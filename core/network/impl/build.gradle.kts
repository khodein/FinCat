import java.util.Properties

plugins {
    id("core-plugin")
    id("org.jetbrains.kotlin.plugin.serialization")
}

android {
    namespace = "com.sergei.pokhodai.expensemanagement.core.network.impl"

    val localProperties = Properties().apply {
        val localPropertiesFile = file("local.properties")
        if (localPropertiesFile.exists()) {
            load(localPropertiesFile.inputStream())
        }
    }

    defaultConfig {
        buildConfigField(
            "String",
            "TG_TOKEN",
            "\"${localProperties.getProperty("tg.token")}\""
        )
        buildConfigField(
            "String",
            "TG_CHAT",
            "\"${localProperties.getProperty("tg.chat")}\""
        )
    }

    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    implementation(project.libs.koin.android)
    implementation(project.libs.koin.core)

    implementation(project.libs.ktor.client.core)
    implementation(project.libs.ktor.client.okhttp)
    implementation(project.libs.ktor.client.android)
    implementation(project.libs.ktor.client.logging)
    implementation(project.libs.ktor.client.content.negotiation)
    implementation(project.libs.ktor.client.cio)
    implementation(project.libs.ktor.serialization.kotlinx.json)
    implementation(project.libs.kotlinx.serialization.json)

    api(project(":core:network:api"))
}