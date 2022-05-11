package com.example.warbyparkerandroid.data.model

import com.example.warbyparkerandroid.R

sealed class GlassFilter(val name: String, val imgId: Int) {
    object Square : GlassFilter("square", R.drawable.filter_square)
    object Rectangle : GlassFilter("rectangle", R.drawable.filter_rectangle)
    object Round : GlassFilter("round", R.drawable.filter_circle)
    object Aviator : GlassFilter("aviator", R.drawable.filter_aviator)
    object CatEye : GlassFilter("cat eye", R.drawable.filter_cateye)
    object Acetate : GlassFilter("acetate", R.drawable.filter_acetate)
    object Metal : GlassFilter("metal", R.drawable.filter_metal)
    object Mixed : GlassFilter("mixed", R.drawable.filter_mixed)

    object Black: GlassFilter("black", R.drawable.black)
    object Tortoise: GlassFilter("tortoise", R.drawable.tortoise)
    object Silver: GlassFilter("black", R.drawable.silver)
    object Grey: GlassFilter("black", R.drawable.grey)
    object Blue: GlassFilter("black", R.drawable.blue)
    object Purple: GlassFilter("black", R.drawable.purple)
    object Red: GlassFilter("black", R.drawable.red)
    object TwoTone: GlassFilter("black", R.drawable.twotone)
    object Brown: GlassFilter("black", R.drawable.brown)
    object Gold: GlassFilter("black", R.drawable.gold)
    object Crystal: GlassFilter("black", R.drawable.crystal_filter)
    object Green: GlassFilter("black", R.drawable.green)
    object Pink: GlassFilter("black", R.drawable.pink)

}
