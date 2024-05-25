@file:Suppress("UnstableApiUsage")

include(":auth")


pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}
rootProject.name = "IoT Teacher Review"
include(":app")
include(":core:network")
include(":data")
include(":core:design")