plugins {
    id("core-plugin")
}

android {
    namespace = "com.sergei.pokhodai.expensemanagement.core.recycler"
}

dependencies {
    implementation(libs.androidx.recyclerview)
}