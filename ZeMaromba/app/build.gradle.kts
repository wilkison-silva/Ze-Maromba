plugins {
    id("com.android.application")
    id(Dependencies.Kotlin.plugin)
    id(Dependencies.Google.Dagger.Hilt.plugin)
    id(Dependencies.Google.DevTools.Ksp.plugin)
}

android {
    namespace = Dependencies.Project.nameSpace
    compileSdk = Dependencies.Project.compileSdk

    defaultConfig {
        applicationId = Dependencies.Project.applicationId
        minSdk = Dependencies.Project.minSdk
        targetSdk = Dependencies.Project.targetSdk
        versionCode = Dependencies.Project.versionCode
        versionName = Dependencies.Project.versionName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        getByName("debug") {
            buildConfigField(
                type = "String",
                name = "DATABASE_NAME",
                value = "\"ze_maromba_app_database_debug\""
            )
            manifestPlaceholders["admob_app_id"] = "ca-app-pub-3940256099942544~3347511713"
            buildConfigField(
                type = "String",
                name = "BANNER_HOME",
                value = "\"ca-app-pub-3940256099942544/6300978111\""
            )
            buildConfigField(
                type = "String",
                name = "BANNER_EXERCISE_FEATURE",
                value = "\"ca-app-pub-3940256099942544/6300978111\""
            )
        }
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )

            buildConfigField(
                type = "String",
                name = "DATABASE_NAME",
                value = "\"ze_maromba_app_database_prod\""
            )
            manifestPlaceholders["admob_app_id"] = "ca-app-pub-6866653583885292~2984319270"
            buildConfigField(
                type = "String",
                name = "BANNER_HOME",
                value = "\"ca-app-pub-6866653583885292/3160769431\""
            )
            buildConfigField(
                type = "String",
                name = "BANNER_EXERCISE_FEATURE",
                value = "\"ca-app-pub-6866653583885292/5677006154\""
            )
        }

        flavorDimensions.add("ze_maromba")

        productFlavors {
            register("internal") {
                dimension = "ze_maromba"

                applicationIdSuffix = ".internal"
                manifestPlaceholders["app_name"] = "Zé Maromba Internal"
                manifestPlaceholders["appIcon"] = "@mipmap/ic_launcher_debug"
                manifestPlaceholders["appIconRound"] = "@mipmap/ic_launcher_debug_round"
            }

            register("prod") {
                dimension = "ze_maromba"

                manifestPlaceholders["app_name"] = "Zé Maromba"
                manifestPlaceholders["appIcon"] = "@mipmap/ic_launcher"
                manifestPlaceholders["appIconRound"] = "@mipmap/ic_launcher_round"
            }
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Dependencies.Project.kotlinCompilerExtensionVersion
    }
    packaging {
        resources {
            excludes.add("/META-INF/{AL2.0,LGPL2.1}")
        }
    }
}

dependencies {

    //-- Kotlin Region
    val kotlinBom = platform(Dependencies.Kotlin.implementationKotlinBom)
    implementation(kotlinBom)
    //--End Region

    //-- Android
    implementation(Dependencies.Android.Lifecycle.implementationLifeCycleRuntime)
    implementation(Dependencies.Android.Activity.implementation)
    implementation(Dependencies.Android.Lifecycle.implementationLifeCycleRuntimeCompose)
    //TODO Remover quando esta lib estiver inserida na ComposeBom
    //Android Compose Navigation
    implementation(Dependencies.Android.Navigation.implementation)
    //--End Region

    //-- End Region

    //-- Compose Region
    val composeBom = platform(Dependencies.Android.Compose.Bom.implementation)
    implementation(composeBom)
    implementation(Dependencies.Android.Compose.Ui.implementationUi)
    implementation(Dependencies.Android.Compose.Ui.implementationUiGraphics)
    implementation(Dependencies.Android.Compose.Ui.implementationUiToolingPreview)
    implementation(Dependencies.Android.Compose.Material.implementationMaterial)
    //--End Region

    //-- Dagger Hilt Region
    implementation(Dependencies.Google.Dagger.Hilt.implementationHiltAndroid)
    ksp(Dependencies.Google.Dagger.Hilt.implementationHiltCompiler)
    ksp(Dependencies.Android.Hilt.implementationHiltCompiler)
    implementation(Dependencies.Android.Hilt.implementationHiltNavigationCompose)
    //-- End Region

    //-- Room Region
    implementation(Dependencies.Android.Room.implementationRoomKtx)
    ksp(Dependencies.Android.Room.implementationRoomCompiler)
    //-- End Region

    //-- DataStore Region
    implementation(Dependencies.Android.DataStore.implementation)
    //-- End Region

    //-- Test Region

    //JUnit
    testImplementation(Dependencies.Test.JUnit.implementation)
    //Android X Architecture Components Tests
    testImplementation(Dependencies.Android.Arch.Core.implementation)
    //Coroutines Test
    testImplementation(Dependencies.Kotlin.Test.implementationCoroutinesTest)
    //MockK
    testImplementation(Dependencies.Test.MockK.implementationMockkAndroid)
    testImplementation(Dependencies.Test.MockK.implementationMockkAgent)
    //Compose Ui Test
    androidTestImplementation(composeBom)
    androidTestImplementation(Dependencies.Android.Compose.Ui.Test.implementationUiTestJUnit4)
    debugImplementation(Dependencies.Android.Compose.Ui.Test.implementationUiTooling)
    debugImplementation(Dependencies.Android.Compose.Ui.Test.implementationUiTestManifest)

    //-- End Region

    //GSON
    implementation(Dependencies.Google.Gson.implementation)

    //Lottie Compose
    implementation(Dependencies.Airbnb.Lottie.implementation)

    //Google AdMob
    implementation(Dependencies.Google.Gms.PlayServicesAds.implementation)
}