object Dependencies {

    object Project {
        const val nameSpace = "br.com.zemaromba"
        const val compileSdk = 34
        const val applicationId = "br.com.zemaromba"
        const val minSdk = 22
        const val targetSdk = 34
        const val versionCode = 8
        const val versionName = "1.1.3"
        const val kotlinCompilerExtensionVersion = "1.5.0"
    }

    object Gradle {
        private const val group = "com.android.tools.build"
        private const val version = "8.1.1"

        const val classpath = "$group:gradle:$version"
    }

    object Kotlin {
        private const val group = "org.jetbrains.kotlin"
        private const val version = "1.9.0"

        const val classpath = "$group:kotlin-gradle-plugin:$version"
        const val implementationKotlinBom = "$group:kotlin-bom:$version"
        const val plugin = "$group.android"

        object Test {
            private const val group = "org.jetbrains.kotlinx"
            private const val version = "1.6.3"

            const val implementationCoroutinesTest = "$group:kotlinx-coroutines-test:$version"
        }
    }

    object Android {
        object Hilt {
            private const val group = "androidx.hilt"
            private const val version = "1.0.0"

            const val implementationHiltNavigationCompose =
                "$group:hilt-navigation-compose:$version"
            const val implementationHiltCompiler = "$group:hilt-compiler:$version"
        }

        object Room {
            private const val group = "androidx.room"
            private const val version = "2.5.2"
            const val implementationRoomKtx = "$group:room-ktx:$version"
            const val implementationRoomCompiler = "$group:room-compiler:$version"
        }

        object DataStore {
            private const val group = "androidx.datastore"
            private const val version = "1.0.0"
            const val implementation = "$group:datastore-preferences:$version"

        }

        object Compose {
            object Bom {
                private const val group = "androidx.compose"
                private const val version = "2023.09.00"

                const val implementation = "$group:compose-bom:$version"
            }

            object Ui {
                private const val group = "androidx.compose.ui"
                const val implementationUi = "$group:ui"
                const val implementationUiGraphics = "$group:ui-graphics"
                const val implementationUiToolingPreview = "$group:ui-tooling-preview"
                const val implementationMaterial = "androidx.compose.material3:material3"

                object Test {
                    const val implementationUiTestJUnit4 = "$group:ui-test-junit4"
                    const val implementationUiTooling = "$group:ui-tooling"
                    const val implementationUiTestManifest = "$group:ui-test-manifest"
                }
            }

            object Material {
                private const val group = "androidx.compose.material3"
                const val implementationMaterial = "$group:material3"
            }
        }

        object Activity {
            private const val group = "androidx.activity"
            private const val version = "1.7.2"

            const val implementation = "$group:activity-compose:$version"
        }

        object Lifecycle {
            private const val group = "androidx.lifecycle"
            private const val version = "2.6.2"
            const val implementationLifeCycleRuntime = "$group:lifecycle-runtime-ktx:$version"
            const val implementationLifeCycleRuntimeCompose =
                "$group:lifecycle-runtime-compose:$version"
        }

        object Navigation {
            private const val group = "androidx.navigation"
            private const val version = "2.7.2"
            const val implementation = "$group:navigation-compose:$version"
        }

        object Arch {
            object Core {
                private const val group = "androidx.arch.core"
                private const val version = "2.2.0"

                const val implementation = "$group:core-testing:$version"
            }
        }
    }

    object Google {
        object Dagger {
            object Hilt {
                private const val group = "com.google.dagger"
                private const val version = "2.48"

                const val classpath = "$group:hilt-android-gradle-plugin:$version"
                const val implementationHiltAndroid = "$group:hilt-android:$version"
                const val implementationHiltCompiler = "$group:hilt-compiler:$version"
                const val plugin = "dagger.hilt.android.plugin"
            }
        }

        object DevTools {
            object Ksp {
                private const val group = "com.google.devtools.ksp"
                const val version = "1.9.0-1.0.13"

                const val plugin = "com.google.devtools.ksp"
            }
        }

        object Gson {
            private const val group = "com.google.code.gson"
            private const val version = "2.9.0"

            const val implementation = "$group:gson:$version"
        }

        object Gms {
            private const val group = "com.google.android.gms"

            object PlayServicesAds {
                private const val version = "22.4.0"
                const val implementation = "$group:play-services-ads:$version"
            }
        }
    }

    object Test {
        object JUnit {
            private const val group = "junit"
            private const val version = "4.13.2"

            const val implementation = "$group:junit:$version"
        }

        object MockK {
            private const val group = "io.mockk"
            private const val version = "1.13.5"

            const val implementationMockkAndroid = "$group:mockk-android:$version"
            const val implementationMockkAgent = "$group:mockk-agent:$version"
        }
    }

    object Airbnb {
        object Lottie {
            private const val group = "com.airbnb.android"
            private const val version = "6.1.0"

            const val implementation = "$group:lottie-compose:$version"
        }
    }

}