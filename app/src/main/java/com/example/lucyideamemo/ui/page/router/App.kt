package com.example.lucyideamemo.ui.page.router

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.lucyideamemo.ui.page.main.MainScreen
import com.moriafly.salt.ui.SaltTheme

fun NavHostController.debouncedPopBackStack() {
    val currentRoute = this.currentBackStackEntry?.destination?.route
    val previousRoute = this.previousBackStackEntry?.destination?.route

    if (currentRoute != null && previousRoute != null) {
        this.popBackStack()
    } else {
        Log.w("Navigation", "Attempted to pop empty stack")
    }
}

@Composable
fun App() {
    val navController = rememberNavController()

    CompositionLocalProvider {
        NavHostContainer(navController = navController)
    }
}


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun NavHostContainer(
    navController: NavHostController
) {
    NavHost(
        navController,
        startDestination = Screen.Main,
    ) {
        composable<Screen.Main> {
            MainScreen(navController = navController)
        }
    }

}



