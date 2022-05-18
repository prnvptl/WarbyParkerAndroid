package com.example.warbyparkerandroid.ui.common

import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun CloseButton(modifier: Modifier = Modifier, tint: Color = MaterialTheme.colors.primary, onSelect: () -> Unit) {
    IconButton(
        onClick = {
            Log.i("CloseButton", " onPressed");
            onSelect()

        }, modifier = modifier
    ) {
        Icon(
            Icons.Filled.Close,
            contentDescription = null,
            tint = tint
        )
    }
}