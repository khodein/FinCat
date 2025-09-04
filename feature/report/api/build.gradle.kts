plugins {
    id("api-plugin")
}

android {
    namespace = "com.sergei.pokhodai.expensemanagement.feature.report.api"
}

dependencies {
    api(project(":feature:category:api"))
}