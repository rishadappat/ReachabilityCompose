pluginManagement {
    repositories {
        maven { url = uri("https://plugins.gradle.org/m2/") }
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

rootProject.name = "ConnectionCompose"
include(":app")
include(":reachability")
