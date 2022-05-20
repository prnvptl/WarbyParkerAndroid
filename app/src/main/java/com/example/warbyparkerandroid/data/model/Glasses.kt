package com.example.warbyparkerandroid.data.model

import android.os.Parcelable
import androidx.compose.animation.core.MutableTransitionState
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class Glasses(
    val id: Long,
    var brand: String,
    val description: String,
    var styles: ArrayList<GlassStyle>,
    val isStackPick: Boolean,
    var visible: @RawValue MutableTransitionState<Boolean> = MutableTransitionState(false),
    val imageIds: List<Int>,
    val descriptionImg: Int,
    val virtualTryOnImg: Int,
    val multiWidthImg: Int,
    val perscriptionImg: Int,
    val whatsIncludedImg: Int
) : Parcelable

@Parcelize
data class GlassStyle(
    val id: Long,
    val name: String,
    val colorGradient: Int,
    val image: Int,
    val price: Float,
    var isFavorite: Boolean,
    val brand: String? = null,
) : Parcelable
