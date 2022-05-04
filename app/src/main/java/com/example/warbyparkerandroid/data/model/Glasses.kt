package com.example.warbyparkerandroid.data.model

data class Glasses(val brand: String, val styles: List<GlassStyle>, val isStackPick: Boolean)

data class GlassStyle(val name: String, val colorGradient: Int, val image: Int, val price: Float, var isFavorite: Boolean, )
