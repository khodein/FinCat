plugins {
    id("core-plugin")
}

android {
    namespace = "com.sergei.pokhodai.expensemanagement.core.support.impl"
}

dependencies {
    implementation(project.libs.koin.android)
    implementation(project.libs.koin.core)
    implementation(project.libs.androidx.preference.ktx)

    api(project(":core:support:api"))
}