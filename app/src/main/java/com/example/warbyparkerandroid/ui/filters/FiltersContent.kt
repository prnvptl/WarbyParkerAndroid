package com.example.warbyparkerandroid.ui.glasses

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.warbyparkerandroid.data.model.GlassFilter
import com.example.warbyparkerandroid.ui.theme.WarbyParkerAndroidTheme
import kotlinx.coroutines.launch

@OptIn(
    ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class,
    ExperimentalMaterialApi::class,
)
@Composable
fun FiltersContent(onClose: () -> Unit) {

    var shapeCount by remember { mutableStateOf(0) }
    var widthsCount by remember { mutableStateOf(0) }
    var materialsCount by remember { mutableStateOf(0) }
    var colorsCount by remember { mutableStateOf(0) }
    var noseBridgeCount by remember { mutableStateOf(0) }

    val filterCountTotal = shapeCount + widthsCount + materialsCount + colorsCount + noseBridgeCount
    val showReset = filterCountTotal > 0

    val shapes = remember {
        mutableStateListOf(
            GlassShapeUI(GlassFilter.Square, false),
            GlassShapeUI(GlassFilter.Rectangle, false),
            GlassShapeUI(GlassFilter.Round, false),
            GlassShapeUI(GlassFilter.Aviator, false),
            GlassShapeUI(GlassFilter.CatEye, false),
        )
    }
    val frameWidths = remember {
        mutableStateListOf(
            FrameWidthUI("Extra narrow", false),
            FrameWidthUI("Narrow", false),
            FrameWidthUI("Medium", false),
            FrameWidthUI("Wide", false),
            FrameWidthUI("Extra wide", false)

        )
    }

    val frameMaterials = remember {
        mutableStateListOf(
            GlassShapeUI(GlassFilter.Acetate, false),
            GlassShapeUI(GlassFilter.Metal, false),
            GlassShapeUI(GlassFilter.Mixed, false),
        )
    }

    val frameColors = remember {
        mutableStateListOf(
            GlassShapeUI(GlassFilter.Black, false),
            GlassShapeUI(GlassFilter.Tortoise, false),
            GlassShapeUI(GlassFilter.Silver, false),
            GlassShapeUI(GlassFilter.Grey, false),
            GlassShapeUI(GlassFilter.Blue, false),
            GlassShapeUI(GlassFilter.Purple, false),
            GlassShapeUI(GlassFilter.Red, false),
            GlassShapeUI(GlassFilter.TwoTone, false),
            GlassShapeUI(GlassFilter.Brown, false),
            GlassShapeUI(GlassFilter.Gold, false),
            GlassShapeUI(GlassFilter.Crystal, false),
            GlassShapeUI(GlassFilter.Green, false),
            GlassShapeUI(GlassFilter.Pink, false),
        )
    }

    val noseBridges = remember {
        mutableStateListOf(
            NoseBridgeUI(
                "Low bridge fit",
                "Larger nose pads, slightly curved temples, ultra-roomy fit, and an adjusted lens tilt provide more comfort for those with low bridges, wide faces, and/or high cheekbones.",
                false
            ),
            NoseBridgeUI(
                "Standard",
                "Typically best for those with a narrow or averaged size face and cheekbones.",
                false
            )
        )
    }

    val resetFilters = {
        shapeCount = 0
        widthsCount = 0
        materialsCount = 0
        colorsCount = 0
        noseBridgeCount = 0
        shapes.forEach { it.selected = false }
        frameWidths.forEach { it.selected = false }
        frameMaterials.forEach { it.selected = false }
        frameColors.forEach { it.selected = false }
        noseBridges.forEach { it.selected = false }
    }

    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = BottomSheetState(BottomSheetValue.Expanded)
    )

    // Filters header animation
    val state = rememberLazyListState()
    val firstItemVisible by remember {
        derivedStateOf {
            state.firstVisibleItemIndex == 0
        }
    }

    val frameCount: Int = when (filterCountTotal) {
        in 1..2 -> {
            152
        }
        in 3..5 -> {
            140
        }
        in 6..10 -> {
            130
        }
        else -> {
            167
        }
    }

    BottomSheetScaffold(
        scaffoldState = bottomSheetScaffoldState,
        modifier = Modifier
            .fillMaxHeight(0.98f),
        sheetShape = RectangleShape,
        sheetContent = {
            FiltersConfirmOverlay(framesCount = frameCount) {
                onClose()
            }
        }) {
        CollapsableHandle()
        Scaffold(
            topBar = {
                FiltersTopBar(onClose = onClose, showReset, firstItemVisible, resetFilters)
            },
        ) {

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
                    .padding(it),
                contentPadding = PaddingValues(horizontal = 12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                state = state
            ) {
                item {
                    Spacer(
                        Modifier
                            .fillMaxWidth()
                            .height(4.dp)
                    )
                }
                item {
                    FrameWidthFilter(
                        widths = frameWidths,
                        widthsCount,
                        { widthsCount++ },
                        { widthsCount-- })
                }

                item {
                    ShapeFilter(
                        "Shape",
                        shapes,
                        shapeCount,
                        onSelect = { shapeCount++ },
                        onDeSelect = { shapeCount-- })
                }

                item {
                    ShapeFilter(
                        "Material",
                        frameMaterials,
                        materialsCount,
                        onSelect = { materialsCount++ },
                        onDeSelect = { materialsCount-- })
                }

                item {
                    ColorFilterImage(
                        frameColors,
                        colorsCount,
                        { colorsCount++ },
                        { colorsCount-- },
                    )
                }

                item {
                    NoseBridgeFilter(
                        noseBridges,
                        noseBridgeCount,
                        { noseBridgeCount++ },
                        { noseBridgeCount-- })
                }
                item {
                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                    )
                }
            }
        }
    }
}

class GlassShapeUI(val shape: GlassFilter, var selected: Boolean)
class FrameWidthUI(val name: String, var selected: Boolean)
class NoseBridgeUI(val title: String, val caption: String, var selected: Boolean)

@Composable
fun FrameWidthFilter(
    widths: List<FrameWidthUI>,
    count: Int,
    onSelect: () -> Unit,
    onDeSelect: () -> Unit
) {
    Column {
        FilterHeader(title = "Frame width", count = count)
        val configuration = LocalConfiguration.current
        widths.windowed(2, 2, true).forEach {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(bottom = 6.dp),
                horizontalArrangement = Arrangement.spacedBy(6.dp),
            ) {
                it.forEach {
                    Box(
                        modifier = Modifier
                            .width(configuration.screenWidthDp.dp / 2)
                            .height(configuration.screenHeightDp.dp / 10)
                            .border(
                                1.dp,
                                color = if (!it.selected) Color.LightGray else MaterialTheme.colors.primaryVariant,
                                shape = MaterialTheme.shapes.medium
                            )
                            .clickable {
                                if (it.selected) {
                                    onDeSelect()
                                } else {
                                    onSelect()
                                }
                                it.selected = !it.selected
                            }
                            .wrapContentSize(Alignment.Center)

                    ) {
                        Text(
                            it.name,
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.button,
                            fontSize = 16.sp
                        )
                    }

                }
            }
        }
    }
}

@Composable
fun ShapeFilter(
    title: String,
    shapes: List<GlassShapeUI>,
    count: Int,
    onSelect: () -> Unit,
    onDeSelect: () -> Unit
) {
    Column {
        FilterHeader(title = title, count = count)
        shapes.windowed(2, 2, true).forEach {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
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
fun ColorFilterImage(
    widths: List<GlassShapeUI>,
    count: Int,
    onSelect: () -> Unit,
    onDeSelect: () -> Unit
) {
    Column {
        FilterHeader(title = "Colors", count = count)
        val configuration = LocalConfiguration.current
        widths.windowed(2, 2, true).forEach {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(6.dp),
            ) {
                it.forEach {
                    Image(
                        painter = painterResource(id = it.shape.imgId),
                        contentDescription = null,
                        modifier = Modifier
                            .width(configuration.screenWidthDp.dp / 2)
                            .height(configuration.screenHeightDp.dp / 10)
                            .border(
                                1.dp,
                                color = if (!it.selected) Color.LightGray else MaterialTheme.colors.primaryVariant,
                                shape = MaterialTheme.shapes.medium
                            )
                            .clickable {
                                if (it.selected) {
                                    onDeSelect()
                                } else {
                                    onSelect()
                                }
                                it.selected = !it.selected
                            },
                        contentScale = ContentScale.FillHeight
                    )
                }
            }
        }
    }
}

@Composable
fun NoseBridgeFilter(
    noseBridges: List<NoseBridgeUI>,
    count: Int,
    onSelect: () -> Unit,
    onDeSelect: () -> Unit
) {
    FilterHeader(title = "Nose Bridge", count)
    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
        noseBridges.forEach {
            NoseBridge(
                it,
                onSelect = onSelect,
                onDeSelect = onDeSelect
            )
        }
    }
}

@Composable
fun NoseBridge(
    noseBridgeUI: NoseBridgeUI,
    onSelect: () -> Unit,
    onDeSelect: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                1.dp,
                color = if (!noseBridgeUI.selected) Color.LightGray else MaterialTheme.colors.primaryVariant,
                shape = MaterialTheme.shapes.medium
            )
            .padding(20.dp)
            .clickable {
                if (noseBridgeUI.selected) {
                    onDeSelect()
                } else {
                    onSelect()
                }
                noseBridgeUI.selected = !noseBridgeUI.selected
            },
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            noseBridgeUI.title,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.button,
            fontSize = 20.sp
        )
        Text(
            noseBridgeUI.caption,
            style = MaterialTheme.typography.caption,
            color = Color.Gray,
            fontSize = 20.sp
        )
    }
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
fun FiltersTopBar(
    onClose: () -> Unit,
    showReset: Boolean,
    isFirstItemVisible: Boolean,
    resetOnClick: () -> Unit
) {
    Column {
        CenterAlignedTopAppBar(
            title = {
                AnimatedVisibility(
                    visible = !isFirstItemVisible,
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    Text(
                        text = "Filters",
                        style = MaterialTheme.typography.h5,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .padding(10.dp)
                            .fillMaxWidth(),
                    )
                }
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
        AnimatedVisibility(
            visible = !isFirstItemVisible,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            androidx.compose.material.Divider(
                modifier = Modifier
                    .fillMaxWidth(),
                thickness = 1.dp,
                color = Color.LightGray
            )
        }
    }
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
    WarbyParkerAndroidTheme {
        FiltersContent {}
    }
}