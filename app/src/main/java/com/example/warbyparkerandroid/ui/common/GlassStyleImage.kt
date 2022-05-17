package com.example.warbyparkerandroid.ui.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp


@Composable
fun GlassStyleImage(colorGradient: Int, onClick: () -> Unit) {
    Image(
        painter = painterResource(colorGradient), contentDescription = null,
        modifier = Modifier
            .height(35.dp)
            .width(35.dp)
            .padding(4.dp)
            .clip(RoundedCornerShape(5.dp))
            .clickable { onClick() }
    )
}
