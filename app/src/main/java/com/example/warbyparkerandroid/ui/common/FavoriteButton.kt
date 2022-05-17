package com.example.warbyparkerandroid.ui.common

import androidx.compose.animation.Crossfade
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.example.warbyparkerandroid.R
import com.example.warbyparkerandroid.data.model.GlassStyle


@Composable
fun FavoriteButton(
    style: GlassStyle,
    isFavorite: Boolean? = null,
    modifier: Modifier,
    onSelect: () -> Unit
) {
    val isFav = if (isFavorite != null) {
        isFavorite
    } else {
        style.isFavorite
    }
    IconButton(onClick = { onSelect() }, modifier = modifier) {
        Crossfade(targetState = isFav) {
            if (it) {
                Icon(
                    painterResource(id = R.drawable.ic_baseline_favorite_24),
                    contentDescription = null,
                    tint = Color.Red
                )
            } else {
                Icon(
                    painterResource(id = R.drawable.ic_baseline_favorite_border_24),
                    contentDescription = null,
                    tint = Color.LightGray
                )
            }
        }
    }
}