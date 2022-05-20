package com.example.warbyparkerandroid.data.model

import androidx.compose.animation.core.MutableTransitionState

data class CartItem(
    val style: GlassStyle,
    val frameWidth: String,
    val prescriptionType: String,
    val lensType: String,
    val lensMaterial: String,
    var visible: MutableTransitionState<Boolean> = MutableTransitionState(false)
)
