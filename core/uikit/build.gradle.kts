plugins {
    id("uikit-plugin")
}

android {
    namespace = "com.sergei.pokhodai.expensemanagement.core.uikit"

    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(project(":core:base"))
    implementation(project(":core:recycler"))
}