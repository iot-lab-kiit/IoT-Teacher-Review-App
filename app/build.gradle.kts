import java.io.FileInputStream
import java.util.Properties

plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)

    // Hilt Dependency Plugin
    alias(libs.plugins.devtools.ksp)
    alias(libs.plugins.androidHilt)

    // Google Services Plugin
    alias(libs.plugins.googleServices)
}

android {
    namespace = "in.iot.lab.teacherreview"
    compileSdk = 34

    defaultConfig {
        applicationId = "in.iot.lab.teacherreview"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
        buildConfigField("String", "BASE_URL", getBaseUrlInCIEnvironment())
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.2"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    // Base Dependencies :----------------------------------------------------
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // Material 3 Dependency
    implementation(libs.androidx.material3)
    implementation(libs.material.icons.extended)
    // Navigation Dependency
    implementation(libs.androidx.navigation)
    // -----------------------------------------------------------------------

    // ViewModel
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.lifecycle.runtime.compose)


    // Material 3 Dependencies
    implementation(libs.androidx.material3)

    // Navigation Dependencies
    implementation(libs.androidx.navigation)

    // Hilt Dependencies
    implementation(libs.com.google.dagger)
    ksp(libs.hilt.compiler)
    implementation(libs.androidx.hilt.navigation)

    //Retrofit 2.0 Dependency
    implementation(libs.retrofit)
    implementation(libs.converter.gson)


    // Firebase Auth
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.auth)
    // Google Legacy
    implementation(libs.com.google.android.gms)


    // Coil
    implementation(libs.coil.kt.compose)

    // Datastore
    implementation(libs.androidx.datastore.preferences)

    // okHttp Dependency
    implementation(libs.okhttp)
    implementation(libs.okhttp.logging.interceptor)

    //Implementing the design module
    implementation(project(":core:design"))

    // Paging 3
    implementation(libs.paging.runtime)
    implementation(libs.paging.compose)
}

fun getBaseUrlInCIEnvironment(): String {
    val propFile = rootProject.file("./local.properties")
    val properties = Properties()
    properties.load(FileInputStream(propFile))
    return properties.getProperty("BASE_URL")
}