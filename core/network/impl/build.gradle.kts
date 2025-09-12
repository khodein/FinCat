plugins {
    id("core-plugin")
    id("org.jetbrains.kotlin.plugin.serialization")
}

android {
    namespace = "com.sergei.pokhodai.expensemanagement.core.network.impl"
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