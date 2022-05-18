package com.example.warbyparkerandroid.ui.common

import androidx.compose.foundation.layout.*
import androidx.compose.material.ExtendedFloatingActionButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun FloatingActionButtons(
    modifier: Modifier,
    buttonOne: String,
    buttonOneTextSize: TextUnit = 18.sp,
    buttonOneBgColor: Color = MaterialTheme.colors.primaryVariant,
    buttonTwo: String,
    ButtonOneIcon: @Composable () -> Unit,
    buttonOneOnClick: () -> Unit,
    buttonTwoOnClick: () -> Unit
) {
    val configuration = LocalConfiguration.current
    val halfWidth = configuration.screenWidthDp / 2
    Row(
        modifier = modifier
            .fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        ExtendedFloatingActionButton(text = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                ButtonOneIcon()
                Text(text = buttonOne, fontSize = buttonOneTextSize, modifier = Modifier.padding(start = 14.dp))
            }
        }, onClick = { buttonOneOnClick() },
            modifier = Modifier
                .height(56.dp)
                .width(halfWidth.dp)
                .padding(start = 10.dp, end = 5.dp),
            backgroundColor = buttonOneBgColor,
            contentColor = Color.White
        )
        ExtendedFloatingActionButton(text = {
            Text(text = buttonTwo, fontSize = 18.sp)
        }, onClick = { buttonTwoOnClick() },
            modifier = Modifier
                .height(56.dp)
                .width(halfWidth.dp)
                .padding(end = 10.dp, start = 5.dp),
            backgroundColor = if(buttonOneBgColor != MaterialTheme.colors.primaryVariant) MaterialTheme.colors.primaryVariant else  MaterialTheme.colors.primary,
            contentColor = Color.White
        )
    }
}