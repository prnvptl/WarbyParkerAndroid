package com.example.warbyparkerandroid.ui.glasses

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.warbyparkerandroid.ui.theme.WarbyParkerAndroidTheme

@Composable
fun FiltersConfirmOverlay(framesCount: Int, onConfirmClick: () -> Unit) {
    var homeTryOnState by remember { mutableStateOf(false) }
    Divider(
        modifier = Modifier
            .fillMaxWidth(),
        thickness = 1.dp,
        color = Color.LightGray
    )
    Column(
        horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier
            .padding(20.dp)
            .fillMaxWidth()
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(16.dp)) {
            Switch(
                checked = homeTryOnState,
                onCheckedChange = { homeTryOnState = !homeTryOnState },
                colors = SwitchDefaults.colors(
                    uncheckedTrackColor = Color.LightGray,
                    checkedTrackColor = MaterialTheme.colors.primaryVariant
                ),
                modifier = Modifier.padding(end = 12.dp)
            )
            Text(
                text = "Available for Home Try-On",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.subtitle1
            )
        }
        Button(
            onClick = { /*TODO*/ },
            colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primaryVariant),
            modifier = Modifier
                .padding(10.dp)
                .clip(MaterialTheme.shapes.large)
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text(
                text = "Show $framesCount Frames",
                style = MaterialTheme.typography.button,
                color = Color.White
            )
        }
    }
}

@Preview()
@Composable
fun Overlay() {
    WarbyParkerAndroidTheme {
        FiltersConfirmOverlay(framesCount = 161) {

        }
    }
}