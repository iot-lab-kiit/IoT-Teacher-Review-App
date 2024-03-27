import java.io.FileInputStream
import java.util.Properties

plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinAndroid)

    // Hilt Dependency
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
}

android {
    namespace = "in.iot.lab.network"
    compileSdk = 34

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
        buildConfigField("String", "BASE_URL", getBaseUrlInCIEnvironment())
    }

    buildTypes {
        release {
            isMinifyEnabled = false
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
        buildConfig = true
    }
}

dependencies {

    // Base Dependencies :----------------------------------------------------
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    //Retrofit 2.0 Dependency
    implementation(libs.retrofit)
    implementation(libs.converter.gson)


    // Hilt Dependencies
    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)

    // okHttp Dependency
    implementation(libs.okhttp)
    implementation(libs.okhttp.logging.interceptor)

    // Firebase Auth
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.auth)
}
fun getBaseUrlInCIEnvironment(): String {
    val propFile = rootProject.file("./local.properties")
    val properties = Properties()
    properties.load(FileInputStream(propFile))
    return properties.getProperty("BASE_URL")
}