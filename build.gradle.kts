// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.jetbrainsKotlinAndroid) apply false
    alias(libs.plugins.androidLibrary) apply false

    // Hilt
    alias(libs.plugins.androidHilt) apply false
    alias(libs.plugins.devtools.ksp) apply false

    // Google Services
    alias(libs.plugins.googleServices) apply false
}