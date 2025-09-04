plugins {
    id("core-plugin")
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.sergei.pokhodai.expensemanagement.core.router"
}

dependencies {
    implementation(project(":core:formatter"))
    implementation(project.libs.kotlinx.serialization.json)
    implementation(project.libs.androidx.navigation.fragment.ktx)
}