plugins {
    id("database-plugin")
}

android {
    namespace = "com.sergei.pokhodai.expensemanagement.database.core.impl"

    ksp {
        arg("room.schemaLocation", "$projectDir/schemas")
    }
}

dependencies {
    api(project(":core:database:api"))
}