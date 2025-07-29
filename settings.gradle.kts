// D:/AirdropsFun/settings.gradle.kts

pluginManagement {
    repositories {
        // Estos son los repositorios donde Gradle busca los PLUGINS.
        // El plugin de Compose está en google() y mavenCentral().
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        // Estos son los repositorios donde Gradle busca las LIBRERÍAS (dependencias).
        google()
        mavenCentral()
    }
}

rootProject.name = "AirdropsFun"
include(":app")
