plugins {
    id("feature-plugin")
}

android {
    namespace = "com.sergei.pokhodai.expensemanagement.feature.user.impl"

    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(project.libs.kotlinx.serialization.json)
    implementation(project.libs.androidx.navigation.fragment.ktx)
    implementation(project.libs.androidx.data.store)

    implementation(project(":core:base"))
    implementation(project(":core:recycler"))
    implementation(project(":core:uikit"))
    implementation(project(":core:router"))
    implementation(project(":core:formatter"))

    api(project(":core:database:api"))
    api(project(":core:eventbus:api"))
    api(project(":core:support:api"))

    api(project(":feature:eventeditor:api"))
    api(project(":feature:category:api"))
    api(project(":feature:user:api"))
}