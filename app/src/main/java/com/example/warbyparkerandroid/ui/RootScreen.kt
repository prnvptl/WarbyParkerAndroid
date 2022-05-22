package com.example.warbyparkerandroid.ui

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.padding
import androidx.compose.material.LocalElevationOverlay
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
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
import com.example.warbyparkerandroid.ui.contacts.Contacts
import com.example.warbyparkerandroid.ui.favorites.Favorites
import com.example.warbyparkerandroid.ui.glasses.EyeGlassesViewModel
import com.example.warbyparkerandroid.ui.glasses.Glasses
import com.example.warbyparkerandroid.ui.pdmeasure.PupillaryDistanceCamera
import com.example.warbyparkerandroid.ui.shop.HomeScreen
import com.example.warbyparkerandroid.ui.theme.WarbyParkerAndroidTheme

@Composable
fun RootScreen() {
    val navController = rememberNavController()
    val viewModel: EyeGlassesViewModel = viewModel()
    WarbyParkerAndroidTheme {
        var showBottomBar by remember { mutableStateOf(true) }
        CompositionLocalProvider(LocalElevationOverlay provides null) {
            Scaffold(bottomBar = {
                AnimatedVisibility(
                    visible = showBottomBar,
                    enter = slideInVertically(initialOffsetY = { it }),
                    exit = slideOutVertically(targetOffsetY = { it }),
                ) {
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
                        }, onContactsClick = {
                            navController.navigate(
                                Route.CONTACTS.name
                            )
                        })
                    }
                    composable(Route.EYEGLASSES.name) {
                        Glasses(
                            onBack = { navController.popBackStack() },
                            showBottomNav = { showBottomBar = true },
                            hideBottomNav = { showBottomBar = false },
                            viewModel = viewModel
                        )
                    }
                    composable(Route.CONTACTS.name) {
                        Contacts(onBack = { navController.popBackStack() })
                    }
                    composable(Route.PD_MEASUREMENT.name) {
                        showBottomBar = false
                        PupillaryDistanceCamera() {
                            Log.i("HAHAHAHAH", "WTF")
                            navController.popBackStack()
                            showBottomBar = true
                        }
                    }
                    composable(Screens.Favorites.route) {
                        Favorites(
                            viewModel,
                            showBottomNav = { showBottomBar = true },
                            hideBottomNav = { showBottomBar = false },
                        ) {
                            navController.navigate(Route.SHOP.name)
                        }
                    }
                    composable(Screens.Account.route) {
                        showBottomBar = true
                        Account(
                            showBottomNav = { showBottomBar = true },
                            onPDMeasurement = { navController.navigate(Route.PD_MEASUREMENT.name) },
                            hideBottomNav = { showBottomBar = false },
                        )
                    }
                    composable(Screens.Cart.route) {
                        showBottomBar = false
                        Cart(onBack = { navController.popBackStack() })
                    }
                }
            }
        }
    }
}