package com.example.warbyparkerandroid.ui.common

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.warbyparkerandroid.R
import com.example.warbyparkerandroid.data.model.GlassStyle

@Composable
fun ColorStyledButtonList(
    selectedStyle: GlassStyle,
    styles: List<GlassStyle>,
    modifier: Modifier,
    showOnlyFavorites: Boolean = false,
    onSelect: (selectedStyle: GlassStyle) -> Unit
) {
    LazyRow(
        modifier = modifier
    ) {
        if(showOnlyFavorites) {
            items(styles, key = { it.id }) {
                if(it.isFavorite) {
                    ColorStyleButton(colorGradient = it.colorGradient, selectedStyle.name == it.name) {
                        onSelect(it)
                    }
                }

            }
        } else {
            items(styles, key = { it.id }) {
                ColorStyleButton(colorGradient = it.colorGradient, selectedStyle.name == it.name) {
                    onSelect(it)
                }
            }
        }
    }
}

@Composable
fun ColorStyleButton(colorGradient: Int, isSelected: Boolean, onSelect: () -> Unit) {
    if (isSelected) {
        OutlinedButton(
            onClick = { onSelect() },
            modifier = Modifier.size(35.dp),  //avoid the oval shape
            shape = CircleShape,
            border = BorderStroke(1.dp, Color.DarkGray),
            contentPadding = PaddingValues(0.dp),  //avoid the little icon
        ) {
            GlassStyleImage(colorGradient = colorGradient) { onSelect() }
        }
    } else {
        GlassStyleImage(colorGradient = colorGradient) { onSelect() }
    }
}