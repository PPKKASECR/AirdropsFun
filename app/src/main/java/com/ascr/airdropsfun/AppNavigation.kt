package com.ascr.airdropsfun

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val authViewModel: AuthViewModel = viewModel()
    val airdropViewModel: AirdropViewModel = viewModel()

    val startDestination = if (FirebaseAuth.getInstance().currentUser != null) {
        Routes.AirdropListScreen.route
    } else {
        Routes.LoginScreen.route
    }

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        // LoginScreen, RegisterScreen, AirdropListScreen, and AirdropDetailScreen
        // composables remain unchanged from the previous step.
        // ...
        composable(Routes.LoginScreen.route) {
            val authState by authViewModel.authState.collectAsState()

            LaunchedEffect(authState) {
                if (authState is AuthState.Success) {
                    navController.navigate(Routes.AirdropListScreen.route) {
                        popUpTo(navController.graph.startDestinationId) { inclusive = true }
                    }
                    authViewModel.resetAuthState()
                }
            }

            LoginScreen(
                authState = authState,
                onLoginClick = { email, pass -> authViewModel.loginUser(email, pass) },
                onNavigateToRegister = {
                    authViewModel.resetAuthState()
                    navController.navigate(Routes.RegisterScreen.route)
                },
                onForgotPasswordClick = { /* TODO */ }
            )
        }

        composable(Routes.RegisterScreen.route) {
            val authState by authViewModel.authState.collectAsState()

            LaunchedEffect(authState) {
                if (authState is AuthState.Success) {
                    navController.navigate(Routes.AirdropListScreen.route) {
                        popUpTo(navController.graph.startDestinationId) { inclusive = true }
                    }
                    authViewModel.resetAuthState()
                }
            }

            RegisterScreen(
                authState = authState,
                onRegisterClick = { email, pass -> authViewModel.registerUser(email, pass) },
                onNavigateToLogin = {
                    authViewModel.resetAuthState()
                    navController.popBackStack()
                }
            )
        }

        composable(Routes.AirdropListScreen.route) {
            val state by airdropViewModel.airdropsState.collectAsState()

            AirdropListScreen(
                state = state,
                onAirdropClick = { airdropId ->
                    navController.navigate(Routes.AirdropDetailScreen.createRoute(airdropId))
                },
                onAddAirdropClick = {
                    navController.navigate(Routes.AddAirdropScreen.route)
                },
                onLogoutClick = {
                    authViewModel.logoutUser()
                    navController.navigate(Routes.LoginScreen.route) {
                        popUpTo(navController.graph.startDestinationId) { inclusive = true }
                    }
                }
            )
        }

        composable(Routes.AirdropDetailScreen.route) { backStackEntry ->
            val airdropId = backStackEntry.arguments?.getString("airdropId")
            LaunchedEffect(airdropId) {
                if (airdropId != null) {
                    airdropViewModel.fetchAirdropById(airdropId)
                }
            }
            val state by airdropViewModel.airdropDetailState.collectAsState()
            AirdropDetailScreen(
                state = state,
                onNavigateBack = { navController.popBackStack() }
            )
        }

        // UPDATE: Connect the onSaveClick lambda to the ViewModel.
        composable(Routes.AddAirdropScreen.route) {
            AddAirdropScreen(
                onNavigateBack = {
                    navController.popBackStack()
                },
                onSaveClick = { titulo, fecha, descripcion, enlace ->
                    // 1. Call the ViewModel to save the data to Firestore.
                    airdropViewModel.saveAirdrop(titulo, fecha, descripcion, enlace)
                    // 2. Navigate back to the list screen.
                    navController.popBackStack()
                }
            )
        }
    }
}