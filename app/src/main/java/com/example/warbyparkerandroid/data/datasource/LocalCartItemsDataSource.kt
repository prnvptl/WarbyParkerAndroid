package com.example.warbyparkerandroid.data.datasource

import com.example.warbyparkerandroid.R
import com.example.warbyparkerandroid.data.model.CartItem
import com.example.warbyparkerandroid.data.model.GlassStyle

val CartItems = listOf<CartItem>(
    CartItem( style = GlassStyle(
        1,
        "Brushed Ink",
        colorGradient = -1,
        image = R.drawable.sahana,
        price = 145F,
        isFavorite = false,
        brand = "Sahana"
    ),
    frameWidth = "Medium",
        prescriptionType = "Single-vision",
        lensType = "Classic",
        lensMaterial = "Polycarbonate"
    ),
    CartItem( style = GlassStyle(
        1,
        "Brushed Ink",
        colorGradient = -1,
        image = R.drawable.sahana,
        price = 145F,
        isFavorite = false,
        brand = "Sahana"
    ),
        frameWidth = "Medium",
        prescriptionType = "Single-vision",
        lensType = "Classic",
        lensMaterial = "Polycarbonate"
    )
)