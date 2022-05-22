package com.example.warbyparkerandroid.ui.bottomnavigation

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.warbyparkerandroid.ui.glasses.EyeGlassesViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun WPBottomNavBar(
    navController: NavController,
    viewModel: EyeGlassesViewModel
) {
    val items = listOf(
        Screens.Shop,
        Screens.Favorites,
        Screens.Account,
        Screens.Cart
    )
    val favState by viewModel.uiState.collectAsState()

    CompositionLocalProvider(LocalElevationOverlay provides null) {
        BottomNavigation(
            backgroundColor = MaterialTheme.colors.background,
            modifier = Modifier.height(64.dp)
        ) {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination
            items.forEach { screen ->
                var selected =
                    currentDestination?.hierarchy?.any {
                        it.route == screen.route
                    } == true
                if (!selected) {
                    selected = currentDestination?.hierarchy?.any {
                        it.route == Route.EYEGLASSES.name
                    } == true &&
                            (screen.route == Route.SHOP.name)
                }
                if (!selected) {
                    selected = currentDestination?.hierarchy?.any {
                        it.route == Route.CONTACTS.name
                    } == true &&
                            (screen.route == Route.SHOP.name)
                }
                val tintColor = if (selected) MaterialTheme.colors.primary else Color.Gray
                BottomNavigationItem(
                    icon = {
                        if (screen.route == Route.FAVORITES.name && favState.favoriteCount > 0) {
                            BadgedBox(badge = {
                                Badge(backgroundColor = MaterialTheme.colors.primaryVariant) {
                                    Text(
                                        favState.favoriteCount.toString(),
                                        color = Color.White
                                    )
                                }
                            }) {
                                Icon(
                                    painterResource(id = screen.iconId),
                                    contentDescription = null,
                                    tint = tintColor,
                                )
                            }
                        } else if (screen.route == Route.CART.name) {
                            BadgedBox(badge = {
                                Badge(backgroundColor = MaterialTheme.colors.primaryVariant) {
                                    Text(
                                        "2",
                                        color = Color.White
                                    )
                                }
                            }) {
                                Icon(
                                    painterResource(id = screen.iconId),
                                    contentDescription = null,
                                    tint = tintColor,
                                )
                            }
                        } else {
                            Icon(
                                painterResource(id = screen.iconId),
                                contentDescription = null,
                                tint = tintColor,
                            )
                        }

                    },
                    label = { Text(stringResource(screen.resourceId), color = tintColor) },
                    selected = selected,
                    onClick = {
                        navController.navigate(screen.route) {
                            // Pop up to the start destination of the graph to
                            // avoid building up a large stack of destinations
                            // on the back stack as users select items
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            // Avoid multiple copies of the same destination when
                            // reselecting the same item
                            launchSingleTop = true
                            // Restore state when reselecting a previously selected item
                            restoreState = true
                        }
                    },
                    modifier = Modifier
                        .fillMaxSize()
                        .fillMaxHeight()
                )
            }
        }
    }
}
