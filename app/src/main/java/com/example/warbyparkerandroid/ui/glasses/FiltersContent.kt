package com.example.warbyparkerandroid.ui.glasses

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Switch
import androidx.compose.material.SwitchColors
import androidx.compose.material.SwitchDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.warbyparkerandroid.data.model.GlassShape

@OptIn(
    ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class,
    ExperimentalMaterialApi::class,
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
    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = BottomSheetState(BottomSheetValue.Expanded)
    )
    BottomSheetScaffold(
        scaffoldState = bottomSheetScaffoldState,
        modifier = Modifier
            .fillMaxHeight(0.98f),
        sheetShape = RectangleShape,
        sheetContent = {
            FiltersConfirmOverlay(framesCount = 161) {}
        }) {
        CollapsableHandle()
        Scaffold(
            topBar = {
                FiltersTopBar(onClose = onClose, showReset, resetFilters)
            },
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
                    .padding(it)
            ) {
                Column {
                    LazyColumn {
                        item {
                            ShapeFilter(
                                shapes,
                                shapeCount,
                                onSelect = { shapeCount++ },
                                onDeSelect = { shapeCount-- })
                        }
                    }
                }

            }
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
        shapes.windowed(2, 2, true).forEach {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                it.forEach {
                    ShapeImage(
                        glassShapeUI = it,
                        onSelect = { onSelect() }) {
                        onDeSelect()
                    }
                }
            }
        }
    }
}


@Composable
fun ShapeImage(
    glassShapeUI: GlassShapeUI,
    onSelect: () -> Unit,
    onDeSelect: () -> Unit
) {
    val configuration = LocalConfiguration.current
    Image(
        painter = painterResource(id = glassShapeUI.shape.imgId),
        contentDescription = null,
        modifier = Modifier
            .width(configuration.screenWidthDp.dp / 2)
            .height(configuration.screenHeightDp.dp / 4)
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
            .padding(12.dp)
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