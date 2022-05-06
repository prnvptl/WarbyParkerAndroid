package com.example.warbyparkerandroid.ui.glasses

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(
    ExperimentalComposeUiApi::class, androidx.compose.material3.ExperimentalMaterial3Api::class,
)
@Composable
fun FiltersContent(onClose: () -> Unit) {
    CollapsableHandle()
    Scaffold(
        topBar = {
            FiltersTopBar(onClose = onClose)
        },
        modifier = Modifier
            .fillMaxHeight(0.98f)
            .background(Color.Red)
    ) {
        Column(modifier = Modifier.fillMaxSize().background(Color.White).padding(it)) {

        }
    }
}

@Composable
fun FiltersTopBar(onClose: () -> Unit) {
    CenterAlignedTopAppBar(
        title = {
            Text("Filters ")

        },
        navigationIcon = {
            IconButton(onClick = { onClose() }) {
                Icon(
                    Icons.Filled.Close,
                    contentDescription = null,
                    tint = MaterialTheme.colors.primary
                )
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color.White)
    )
}
@Composable
fun CollapsableHandle() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentSize(Alignment.Center)
            .padding(top = 10.dp)
    ) {
        Box(
            modifier = Modifier
                .height(5.dp)
                .width(60.dp)
                .clip(RoundedCornerShape(5.dp))
                .background(Color.LightGray)
        )
    }
}

@Composable
@Preview
fun Filters() {
    FiltersContent {

    }
}