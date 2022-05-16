package com.example.warbyparkerandroid.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Glasses(
    val id: Long,
    var brand: String,
    val description: String,
    var styles: ArrayList<GlassStyle>,
    val isStackPick: Boolean,
    var visible: Boolean = false,
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
    var isFavorite: Boolean
) : Parcelable
