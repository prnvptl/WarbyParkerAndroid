package com.example.warbyparkerandroid.ui.glasses

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.warbyparkerandroid.R
import com.example.warbyparkerandroid.data.datasource.AllGlasses
import com.example.warbyparkerandroid.data.model.Glasses

@Composable
fun Glasses(onBack: () -> Unit, glasses: List<Glasses> = AllGlasses) {
    val state = rememberLazyListState()
    val firstItemVisible by remember {
        derivedStateOf {
            state.firstVisibleItemIndex == 0
        }
    }
    Scaffold(
        topBar = {
            CenterTopAppBar(onBack = onBack, firstItemVisible)
        }
    ) {
        LazyColumn(
            state = state,
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            contentPadding = PaddingValues(8.dp)
        ) {
            item {
                Text(
                    text = "Eyeglasses",
                    style = MaterialTheme.typography.h5,
                    modifier = Modifier.padding(10.dp)
                )
            }
            items(glasses) {
                GlassesItem(glasses = it)
            }
        }
    }
}

@Composable
fun GlassesItem(glasses: Glasses) {
    var selectedStyle by remember { mutableStateOf(glasses.styles[0]) }
    Column(
        modifier = Modifier.fillMaxWidth(),
    ) {
        Image(
            painter = painterResource(R.drawable.staffpick), contentDescription = null,
            modifier = Modifier
                .width(65.dp)
                .height(65.dp)
        )
        Image(
            painter = painterResource(selectedStyle.image), contentDescription = null,
            modifier = Modifier
                .height(130.dp)
                .fillMaxWidth()
        )
        Text(
            text = glasses.brand,
            style = MaterialTheme.typography.h4,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)
        ) {
            LazyRow(
                modifier = Modifier
                    .align(Alignment.Center)
            ) {
                items(glasses.styles) {
                    StyleButton(colorGradient = it.colorGradient, selectedStyle.name == it.name) {
                        selectedStyle = it
                    }
                }
            }
            val favIcon =
                if (selectedStyle.isFavorite) R.drawable.ic_baseline_favorite_24 else R.drawable.ic_baseline_favorite_border_24
            val tintColor = if (selectedStyle.isFavorite) Color.Red else Color.LightGray
            IconButton(onClick = { }, modifier = Modifier.align(Alignment.TopEnd)) {
                Icon(painterResource(id = favIcon), contentDescription = null, tint = tintColor)
            }
        }

    }
}

@Composable
fun StyleButton(colorGradient: Int, isSelected: Boolean, onSelect: () -> Unit) {
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


@Preview
@Composable
fun GlassesPreview() {
    Glasses({})
}

@Composable
fun CenterTopAppBar(onBack: () -> Unit, isFirstItemVisible: Boolean) {
    val tintColor = MaterialTheme.colors.primary
    CenterAlignedTopAppBar(
        title = {
            AnimatedVisibility(
                visible = !isFirstItemVisible,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                Text(
                    text = "Eyeglasses",
                    style = MaterialTheme.typography.h5,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(10.dp)
                        .fillMaxWidth(),
                )
            }
        },
        navigationIcon = {
            IconButton(onClick = { onBack() }) {
                Icon(Icons.Filled.ArrowBack, contentDescription = null, tint = tintColor)
            }
        },
        actions = {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    painterResource(id = R.drawable.ic_baseline_filter_list_24),
                    contentDescription = null,
                    tint = tintColor
                )
            }
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = null,
                    tint = tintColor
                )
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color.White)
    )
}

