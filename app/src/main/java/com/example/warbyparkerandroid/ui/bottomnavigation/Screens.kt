package com.example.warbyparkerandroid.ui.bottomnavigation

import androidx.annotation.StringRes
import com.example.warbyparkerandroid.R

sealed class Screens(val route: String, @StringRes val resourceId: Int,  val iconId: Int) {
    object Shop : Screens(Route.SHOP.name, R.string.shop, R.drawable.ic_baseline_storefront_24)
    object Favorites : Screens(Route.FAVORITES.name, R.string.favorites, R.drawable.ic_baseline_favorite_border_24)
    object Account : Screens(Route.ACCOUNT.name, R.string.account, R.drawable.ic_baseline_person_outline_24)
    object Cart : Screens(Route.CART.name, R.string.cart, R.drawable.ic_outline_shopping_cart_24)
}

enum class Route(route: String) {
    SHOP("shop"),
    EYEGLASSES("eyeglasses"),
    FAVORITES("favorites"),
    ACCOUNT("account"),
    CART("cart")
}