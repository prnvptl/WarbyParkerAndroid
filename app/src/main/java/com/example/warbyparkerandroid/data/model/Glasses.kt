package com.example.warbyparkerandroid.data.model

import androidx.compose.animation.core.MutableTransitionState

data class Glasses(var brand: String, var styles: ArrayList<GlassStyle>, val isStackPick: Boolean, var visible: MutableTransitionState<Boolean> = MutableTransitionState(false))

data class GlassStyle(val name: String, val colorGradient: Int, val image: Int, val price: Float, var isFavorite: Boolean)
