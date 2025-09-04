plugins {
    id("feature-plugin")
}

android {
    namespace = "com.sergei.pokhodai.expensemanagement.feature.settings.impl"

    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(project.libs.kotlinx.serialization.json)
    implementation(project.libs.androidx.navigation.fragment.ktx)

    implementation(project(":core:base"))
    implementation(project(":core:recycler"))
    implementation(project(":core:uikit"))
    implementation(project(":core:router"))

    api(project(":database:api"))

    api(project(":core:eventbus:api"))
    api(project(":core:support:api"))

    api(project(":feature:settings:api"))
    api(project(":feature:user:api"))
}