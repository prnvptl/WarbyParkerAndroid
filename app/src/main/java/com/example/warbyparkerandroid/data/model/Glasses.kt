package com.example.warbyparkerandroid.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Glasses(
    val id: Long,
    var brand: String,
    var styles: ArrayList<GlassStyle>,
    val isStackPick: Boolean,
    var visible: Boolean = false,
    val imageIds: List<Int>
) : Parcelable

@Parcelize
data class GlassStyle(
    val id: Long,
    val name: String,
    val colorGradient: Int,
    val image: Int,
    val price: Float,
    var isFavorite: Boolean
) : Parcelable
