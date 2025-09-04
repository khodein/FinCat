plugins {
    id("core-plugin")
}

android {
    namespace = "com.sergei.pokhodai.expensemanagement.core.eventbus.impl"
}

dependencies {
    implementation(project.libs.koin.android)
    implementation(project.libs.koin.core)

    api(project(":core:eventbus:api"))
}