package com.example.warbyparkerandroid.ui.glasses

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.warbyparkerandroid.data.model.GlassShape

@OptIn(
    ExperimentalComposeUiApi::class, androidx.compose.material3.ExperimentalMaterial3Api::class,
)
@Composable
fun FiltersContent(onClose: () -> Unit) {

    var shapeCount by remember { mutableStateOf(0) }
    val showReset = shapeCount > 0
    val shapes = remember {
        mutableStateListOf(
            GlassShapeUI(GlassShape.Square, false),
            GlassShapeUI(GlassShape.Rectangle, false),
            GlassShapeUI(GlassShape.Round, false),
            GlassShapeUI(GlassShape.Aviator, false),
            GlassShapeUI(GlassShape.CatEye, false),
        )
    }
    val resetFilters = {
        shapeCount = 0
        shapes.forEach { it.selected = false }
    }
    CollapsableHandle()
    Scaffold(
        topBar = {
            FiltersTopBar(onClose = onClose, showReset, resetFilters)
        },
        modifier = Modifier
            .fillMaxHeight(0.98f)
            .background(Color.Red)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(it)
        ) {
//            FilterHeader(title = "Shape", count = 1)
            ShapeFilter(
                shapes,
                shapeCount,
                onSelect = { shapeCount++ },
                onDeSelect = { shapeCount-- })
        }
    }
}

class GlassShapeUI(val shape: GlassShape, var selected: Boolean)

@Composable
fun ShapeFilter(
    shapes: List<GlassShapeUI>,
    count: Int,
    onSelect: () -> Unit,
    onDeSelect: () -> Unit
) {
    Column {
        FilterHeader(title = "Shape", count = count)
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            content = {
                items(shapes) {
                    ShapeImage(glassShapeUI = it, onSelect = { onSelect() }) {
                        onDeSelect()
                    }
                }
            },
        )
    }
}


@Composable
fun ShapeImage(glassShapeUI: GlassShapeUI, onSelect: () -> Unit, onDeSelect: () -> Unit) {
    Image(
        painter = painterResource(id = glassShapeUI.shape.imgId),
        contentDescription = null,
        modifier = Modifier
            .fillMaxSize()
            .border(
                1.dp,
                color = if (!glassShapeUI.selected) Color.LightGray else MaterialTheme.colors.primaryVariant,
                shape = MaterialTheme.shapes.medium
            )
            .clickable {
                if (glassShapeUI.selected) {
                    onDeSelect()
                } else {
                    onSelect()
                }
                glassShapeUI.selected = !glassShapeUI.selected
            },
        contentScale = ContentScale.FillWidth,
    )
}

@Composable
fun FilterHeader(title: String, count: Int) {
    Row(
        Modifier
            .fillMaxWidth()
            .wrapContentSize(Alignment.Center)
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.h4,
            modifier = Modifier.padding(end = 8.dp)
        )
        Box(
            modifier = Modifier
                .height(30.dp)
                .width(30.dp)
                .fillMaxSize()
                .aspectRatio(1f)
                .background(
                    color = if (count > 0) MaterialTheme.colors.primaryVariant else Color.Gray,
                    shape = CircleShape
                )
                .align(Alignment.CenterVertically),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = count.toString(),
                style = MaterialTheme.typography.h6,
                textAlign = TextAlign.Center,
                color = Color.White
            )
        }
    }
}

@Composable
fun FiltersTopBar(onClose: () -> Unit, showReset: Boolean, resetOnClick: () -> Unit) {
    CenterAlignedTopAppBar(
        title = {},
        navigationIcon = {
            IconButton(onClick = { onClose() }) {
                Icon(
                    Icons.Filled.Close,
                    contentDescription = null,
                    tint = MaterialTheme.colors.primary
                )
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color.White),
        actions = {
            if (showReset) {
                Text("Reset",
                    color = MaterialTheme.colors.primary,
                    style = MaterialTheme.typography.h6,
                    modifier = Modifier
                        .padding(end = 12.dp)
                        .clickable {
                            resetOnClick()
                        })
            }
        }
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