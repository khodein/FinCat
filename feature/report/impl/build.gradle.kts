plugins {
    id("feature-plugin")
}

android {
    namespace = "com.sergei.pokhodai.expensemanagement.feature.report.impl"

    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(project.libs.kotlinx.serialization.json)
    implementation(project.libs.androidx.navigation.fragment.ktx)
    implementation(project.libs.kotlinx.datetime)
    implementation(project.libs.apache.poi)
    implementation(project.libs.apache.poi.ooxml)

    implementation(project(":core:base"))
    implementation(project(":core:recycler"))
    implementation(project(":core:uikit"))
    implementation(project(":core:router"))
    implementation(project(":core:formatter"))

    api(project(":database:api"))

    api(project(":core:eventbus:api"))
    api(project(":core:support:api"))

    api(project(":feature:eventeditor:api"))
    api(project(":feature:category:api"))
    api(project(":feature:report:api"))
    api(project(":feature:calendar:api"))
}