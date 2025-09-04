plugins {
    id("core-plugin")
}

android {
    namespace = "com.sergei.pokhodai.expensemanagement.core.formatter"
}

dependencies {
    implementation(project.libs.kotlinx.datetime)
}