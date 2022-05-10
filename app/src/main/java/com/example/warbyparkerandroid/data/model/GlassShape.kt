package com.example.warbyparkerandroid.data.model

import com.example.warbyparkerandroid.R

sealed class GlassShape(val name: String, val imgId: Int) {
    object Square : GlassShape("square", R.drawable.filter_square)
    object Rectangle : GlassShape("rectangle", R.drawable.filter_rectangle)
    object Round : GlassShape("round", R.drawable.filter_circle)
    object Aviator : GlassShape("aviator", R.drawable.filter_aviator)
    object CatEye : GlassShape("cat eye", R.drawable.filter_cateye)
}
