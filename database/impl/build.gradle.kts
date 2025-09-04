plugins {
    id("database-plugin")
}

android {
    namespace = "com.sergei.pokhodai.expensemanagement.database.impl"

    ksp {
        arg("room.schemaLocation", "$projectDir/schemas")
    }
}

dependencies {
    api(project(":database:api"))
}