plugins {
    id("api-plugin")
}

android {
    namespace = "com.sergei.pokhodai.expensemanagement.core.support.api"
}

dependencies {
    implementation(project.libs.androidx.navigation.fragment.ktx)
    implementation(project(":core:formatter"))
}