package com.ascr.airdropsfun

/**
 * Defines all the navigation routes in the application in a type-safe way.
 * Using a sealed class ensures that only the defined routes can be used.
 */
sealed class Routes(val route: String) {
    data object SplashScreen : Routes("splash_screen")
    data object LoginScreen : Routes("login_screen")
    data object RegisterScreen : Routes("register_screen")
    data object AirdropListScreen : Routes("airdrop_list_screen")
    data object AboutAppScreen : Routes("about_app_screen")

    // CORRECTION: Add the new route for the AddAirdropScreen.
    data object AddAirdropScreen : Routes("add_airdrop_screen")

    // Route for a screen that requires an argument (the airdrop ID)
    data object AirdropDetailScreen : Routes("airdrop_detail_screen/{airdropId}") {
        // Helper function to create the route with a specific ID
        fun createRoute(airdropId: String) = "airdrop_detail_screen/$airdropId"
    }
}