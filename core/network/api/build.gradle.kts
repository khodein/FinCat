plugins {
    id("api-plugin")
    id("org.jetbrains.kotlin.plugin.serialization")
}

android {
    namespace = "com.sergei.pokhodai.expensemanagement.core.network.api"
}

dependencies {
    implementation(project.libs.kotlinx.serialization.json)
}