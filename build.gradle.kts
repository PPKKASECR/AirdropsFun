// D:/AirdropsFun/build.gradle.kts
// (Este archivo está en la carpeta principal del proyecto)

plugins {
    // Declara los plugins para que los módulos los puedan usar
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    id("org.jetbrains.kotlin.plugin.compose") version libs.versions.composeCompiler.get() apply false
    id("com.google.gms.google-services") version libs.versions.googleServices.get() apply false
}
