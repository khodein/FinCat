plugins {
    id("app-plugin")
}

android {
    namespace = "com.sergei.pokhodai.expensemanagement"

    defaultConfig {
        applicationId = "com.sergei.pokhodai.expensemanagement"
    }

    buildFeatures {
        buildConfig = true
        viewBinding = true
    }
}

dependencies {
    implementation(project(":core:uikit"))
    implementation(project(":core:recycler"))
    implementation(project(":core:base"))
    implementation(project(":core:router"))
    implementation(project(":core:formatter"))

    implementation(project(":core:network:impl"))
    api(project(":core:network:api"))

    implementation(project(":core:eventbus:impl"))
    api(project(":core:eventbus:api"))

    implementation(project(":core:support:impl"))
    api(project(":core:support:api"))

    implementation(project(":database:impl"))
    api(project(":database:api"))

    implementation(project(":feature:category:impl"))
    api(project(":feature:category:api"))

    implementation(project(":feature:eventeditor:impl"))
    api(project(":feature:eventeditor:api"))

    implementation(project(":feature:home:impl"))
    api(project(":feature:home:api"))

    implementation(project(":feature:report:impl"))
    api(project(":feature:report:api"))

    implementation(project(":feature:settings:impl"))
    api(project(":feature:settings:api"))

    implementation(project(":feature:user:impl"))
    api(project(":feature:user:api"))

    implementation(project(":feature:calendar:impl"))
    api(project(":feature:calendar:api"))

    implementation(project(":feature:exchangerate:impl"))
    api(project(":feature:exchangerate:api"))

    implementation(project(":feature:faq:impl"))
    api(project(":feature:faq:api"))
}

