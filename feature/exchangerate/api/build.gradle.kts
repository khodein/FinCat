plugins {
    id("api-plugin")
}

android {
    namespace = "com.sergei.pokhodai.expensemanagement.feature.exchangerate.api"
}

dependencies {
    implementation(project(":core:formatter"))
}