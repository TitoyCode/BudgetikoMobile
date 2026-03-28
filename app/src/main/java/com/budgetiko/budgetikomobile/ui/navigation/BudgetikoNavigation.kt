package com.budgetiko.budgetikomobile.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.budgetiko.budgetikomobile.data.network.BudgetikoRepository
import com.budgetiko.budgetikomobile.ui.screens.auth.LoginScreen
import com.budgetiko.budgetikomobile.ui.screens.auth.RegisterScreen
import com.budgetiko.budgetikomobile.ui.screens.main.MainScreen

@Composable
fun BudgetikoNavigation() {
    val context = LocalContext.current
    val repository = remember { BudgetikoRepository.getInstance(context) }
    val navController = rememberNavController()
    val startDestination = if (repository.hasSession()) "main" else "login"

    NavHost(navController = navController, startDestination = startDestination) {
        composable("login") {
            LoginScreen(
                    onNavigateToRegister = { navController.navigate("register") },
                    onLoginSuccess = {
                        navController.navigate("main") { popUpTo("login") { inclusive = true } }
                    }
            )
        }
        composable("register") {
            RegisterScreen(
                    onNavigateToLogin = { navController.popBackStack() },
                    onRegisterSuccess = {
                        navController.navigate("main") { popUpTo("login") { inclusive = true } }
                    }
            )
        }
        composable("main") {
            MainScreen(
                    onLogout = {
                        repository.logout()
                        navController.navigate("login") { popUpTo("main") { inclusive = true } }
                    }
            )
        }
    }
}
