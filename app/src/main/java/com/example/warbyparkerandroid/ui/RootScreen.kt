package com.example.warbyparkerandroid.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.padding
import androidx.compose.material.LocalElevationOverlay
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.warbyparkerandroid.ui.account.Account
import com.example.warbyparkerandroid.ui.bottomnavigation.Route
import com.example.warbyparkerandroid.ui.bottomnavigation.Screens
import com.example.warbyparkerandroid.ui.bottomnavigation.WPBottomNavBar
import com.example.warbyparkerandroid.ui.cart.Cart
import com.example.warbyparkerandroid.ui.favorites.Favorites
import com.example.warbyparkerandroid.ui.glasses.EyeGlassesViewModel
import com.example.warbyparkerandroid.ui.glasses.Glasses
import com.example.warbyparkerandroid.ui.shop.HomeScreen
import com.example.warbyparkerandroid.ui.theme.WarbyParkerAndroidTheme

@Composable
fun RootScreen() {
    val navController = rememberNavController()
    val viewModel: EyeGlassesViewModel = viewModel()
//    val bottomBarState = rememberSaveable { (mutableStateOf(true)) }
    WarbyParkerAndroidTheme {
        var showBottomBar by remember { mutableStateOf(true) }
//        val favCount = viewModel.favoritesCount
        CompositionLocalProvider(LocalElevationOverlay provides null) {
            Scaffold(bottomBar = {
                    AnimatedVisibility(
                        visible = showBottomBar,
                        enter = slideInVertically(initialOffsetY = { it }),
                        exit = slideOutVertically(targetOffsetY = { it }),
                    ){
                        WPBottomNavBar(navController = navController, viewModel)
                    }

            }) { innerPadding ->
                NavHost(
                    navController,
                    startDestination = Screens.Shop.route,
                    Modifier.padding(innerPadding)
                ) {
                    composable(Screens.Shop.route) {
                        showBottomBar = true
                        HomeScreen(onEyeglassesShopClick = {
                            navController.navigate(
                                Route.EYEGLASSES.name
                            )
                        })
                    }
                    composable(Route.EYEGLASSES.name) {
//                        showBottomBar = true
                        Glasses(
                            onBack = { navController.popBackStack() },
                            showBottomNav = { showBottomBar = true },
                            hideBottomNav = { showBottomBar = false },
                            viewModel = viewModel
                        )
                    }
                    composable(Screens.Favorites.route) {
                        showBottomBar = true
                        Favorites(viewModel) {
                            navController.navigate(Route.SHOP.name)
                        }
                    }
                    composable(Screens.Account.route) {
                        showBottomBar = true
                        Account() }
                    composable(Screens.Cart.route) {
                        showBottomBar = false
                        Cart(onBack = { navController.popBackStack() },)
                    }
                }
            }
        }
    }
}