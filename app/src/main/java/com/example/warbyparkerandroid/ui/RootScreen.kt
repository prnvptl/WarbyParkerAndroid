package com.example.warbyparkerandroid.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.warbyparkerandroid.ui.bottomnavigation.Screens
import com.example.warbyparkerandroid.ui.bottomnavigation.WPBottomNavBar

@Composable
fun RootScreen() {
    val navController = rememberNavController()
    Scaffold(bottomBar = {
        WPBottomNavBar(navController = navController)
    }) { innerPadding ->
        NavHost(
            navController,
            startDestination = Screens.Shop.route,
            Modifier.padding(innerPadding)
        ) {
            composable(Screens.Shop.route) { Text(text = "Shop!") }
            composable(Screens.Favorites.route) { Text(text = "Favorites!") }
            composable(Screens.Account.route) { Text(text = "Account!") }
            composable(Screens.Cart.route) { Text(text = "Cart!") }
        }
    }
}