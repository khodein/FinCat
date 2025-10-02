plugins {
    id("api-plugin")
}

android {
    namespace = "com.sergei.pokhodai.expensemanagement.database.core.api"
}

dependencies {
    implementation(project.libs.androidx.room.runtime)
}