plugins {
    id("core-plugin")
}

android {
    namespace = "com.sergei.pokhodai.expensemanagement.core.base"

    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.viewbinding)
    implementation(libs.android.material)
    implementation(libs.kotlinx.datetime)

    implementation(project(":core:formatter"))
}