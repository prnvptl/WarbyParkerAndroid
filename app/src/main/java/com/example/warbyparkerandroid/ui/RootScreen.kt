package com.example.warbyparkerandroid.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material.LocalElevationOverlay
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.warbyparkerandroid.ui.bottomnavigation.Route
import com.example.warbyparkerandroid.ui.bottomnavigation.Screens
import com.example.warbyparkerandroid.ui.bottomnavigation.WPBottomNavBar
import com.example.warbyparkerandroid.ui.favorites.Favs
import com.example.warbyparkerandroid.ui.glassdetail.GlassDetail
import com.example.warbyparkerandroid.ui.glasses.EyeGlassesViewModel
import com.example.warbyparkerandroid.ui.glasses.Glasses
import com.example.warbyparkerandroid.ui.shop.HomeScreen
import com.example.warbyparkerandroid.ui.theme.WarbyParkerAndroidTheme

@Composable
fun RootScreen() {
    val navController = rememberNavController()
    val viewModel: EyeGlassesViewModel = viewModel()
    WarbyParkerAndroidTheme {
        var showBottomBar by remember { mutableStateOf(true) }
//        val favCount = viewModel.favoritesCount
        CompositionLocalProvider(LocalElevationOverlay provides null) {
            Scaffold(bottomBar = {
                if(showBottomBar) WPBottomNavBar(navController = navController, viewModel)
            }) { innerPadding ->
                NavHost(
                    navController,
                    startDestination = Screens.Shop.route,
                    Modifier.padding(innerPadding)
                ) {
                    composable(Screens.Shop.route) {
                        HomeScreen(onEyeglassesShopClick = {
                            navController.navigate(
                                Route.EYEGLASSES.name
                            )
                        })
                    }
                    composable(Route.EYEGLASSES.name) {
                        Glasses(
                            onBack = { navController.popBackStack() },
                            showBottomNav = { showBottomBar = true },
                            hideBottomNav = { showBottomBar = false },
                        viewModel = viewModel)
                    }
                    composable(Screens.Favorites.route) { Favs(viewModel) }
                    composable(Screens.Account.route) { Text(text = "Account!") }
                    composable(Screens.Cart.route) { Text(text = "Cart!") }
                }
            }
        }
    }
}