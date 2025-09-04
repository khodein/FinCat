plugins {
    id("api-plugin")
}

android {
    namespace = "com.sergei.pokhodai.expensemanagement.feature.eventeditor.api"
}

dependencies {
    implementation(project.libs.kotlinx.datetime)
    implementation(project(":core:formatter"))

    api(project(":feature:category:api"))
}