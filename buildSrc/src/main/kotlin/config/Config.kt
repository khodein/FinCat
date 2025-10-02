package config

import com.android.build.gradle.BaseExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

object Config {
    private const val FOCUS_SDK = 35
    private const val MIN_SDK = 26
    private const val VERSION_CODE = 2

    private const val MAJOR = 1
    private const val MINOR = 0
    private const val PATCH = 1

    private const val VERSION_NAME = "${MAJOR}.${MINOR}.${PATCH}"

    operator fun invoke(project: Project) {
        project.extensions.configure<BaseExtension> {
            compileSdkVersion(FOCUS_SDK)

            defaultConfig {
                minSdk = MIN_SDK
                targetSdk = FOCUS_SDK
                versionCode = VERSION_CODE
                versionName = VERSION_NAME
            }

            buildTypes {
                getByName("release") {
//                    isMinifyEnabled = true
                    isDebuggable = false

                    proguardFiles(
                        getDefaultProguardFile("proguard-android-optimize.txt"),
                        "proguard-rules.pro"
                    )
                }

                getByName("debug") {
//                    isMinifyEnabled = false
                    isDebuggable = true
                }

                compileOptions {
                    sourceCompatibility = JavaVersion.VERSION_11
                    targetCompatibility = JavaVersion.VERSION_11
                }

                project.tasks.withType<KotlinCompile>().configureEach {
                    compilerOptions {
                        jvmTarget.set(JvmTarget.JVM_11)
                    }
                }
            }
        }
    }
}