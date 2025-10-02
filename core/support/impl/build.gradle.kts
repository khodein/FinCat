plugins {
    id("core-plugin")
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.sergei.pokhodai.expensemanagement.core.support.impl"
}

dependencies {
    implementation(project.libs.koin.android)
    implementation(project.libs.koin.core)
    implementation(project.libs.androidx.preference.ktx)
    implementation(project.libs.kotlinx.serialization.json)
    implementation(project.libs.github.skydoves.color.picker)
    implementation(project.libs.android.material)
    implementation(project.libs.kotlinx.datetime)

    implementation(project(":core:formatter"))
    implementation(project(":core:base"))

    api(project(":core:support:api"))
}