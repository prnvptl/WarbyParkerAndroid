package com.example.warbyparkerandroid.ui.glassdetail

import android.content.pm.ConfigurationInfo
import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.warbyparkerandroid.data.model.GlassStyle
import com.example.warbyparkerandroid.data.model.Glasses
import com.example.warbyparkerandroid.ui.glasses.CollapsableHandle
import com.example.warbyparkerandroid.ui.theme.WarbyParkerAndroidTheme
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState

@OptIn(ExperimentalMaterialApi::class, ExperimentalPagerApi::class)
@Composable
fun GlassDetail(glass: Glasses, selectedStyle: GlassStyle) {
    CompositionLocalProvider(LocalElevationOverlay provides null) {
        WarbyParkerAndroidTheme {
            val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
                bottomSheetState = BottomSheetState(BottomSheetValue.Expanded)
            )
            var pagerSelection = remember {
                mutableStateListOf(true, false, false)
            }
            BottomSheetScaffold(
                scaffoldState = bottomSheetScaffoldState,
                sheetShape = RoundedCornerShape(8.dp),
                sheetElevation = 8.dp,
                sheetPeekHeight = 100.dp,
                sheetContent = {
                    Scaffold {
                        HeaderContainer(
                            title = glass.brand,
                            bottomSheetState = bottomSheetScaffoldState.bottomSheetState
                        ) {
                            Column {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(top = 18.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    IconButton(
                                        onClick = {},
                                        modifier = Modifier.padding(start = 8.dp)
                                    ) {
                                        Icon(
                                            Icons.Filled.Close,
                                            contentDescription = null,
                                            tint = MaterialTheme.colors.primary
                                        )
                                    }
                                    LazyRow(contentPadding = PaddingValues(top = 18.dp, end = 8.dp)) {
                                        items(pagerSelection) {
                                            Canvas(modifier = Modifier
                                                .size(13.dp)
                                                .padding(end = 6.dp), onDraw = {
                                                drawCircle(color = if(it) Color.Gray else Color.LightGray,)
                                            })
                                        }
                                    }
                                }
                                val pagerState = rememberPagerState()
                                LaunchedEffect(pagerState) {
                                    // Collect from the pager state a snapshotFlow reading the currentPage
                                    snapshotFlow { pagerState.currentPage }.collect { page ->
                                        pagerSelection.clear()
                                        val newItems = listOf(page == 0, page == 1, page == 2)
                                        pagerSelection.addAll(newItems)
                                    }
                                }
                                // Display 10 items
                                val configuration = LocalConfiguration.current

                                HorizontalPager(count = 3,
                                    state = pagerState,
                                    modifier = Modifier
//                                        .fillMaxSize()
                                        .fillMaxWidth()
                                        .height((configuration.screenHeightDp/2.5).dp)
                                        .background(Color.Blue)
                                ) { page ->
                                    // Our page content
                                    Image(painter = painterResource(id = glass.imageIds[page]), contentDescription = null, modifier =  Modifier.fillMaxSize(), contentScale = ContentScale.FillWidth)
//                                    Text(
//                                        text = "Page: $page",
//                                        modifier = Modifier.fillMaxWidth()
//                                    )
                                }
                            }
                        }
                    }
                },
                backgroundColor = Color.Transparent
            ) {

            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HeaderContainer(
    title: String,
    bottomSheetState: BottomSheetState,
    content: @Composable BoxScope.() -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.primaryVariant),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (bottomSheetState.isCollapsed) {
            CollapsableHandle()
        }
        val spacerPadding =
            if (bottomSheetState.isExpanded) 60.dp else 10.dp
        Spacer(
            Modifier
                .fillMaxWidth()
                .height(spacerPadding)
        )
        val header =
            if (bottomSheetState.isExpanded) "Pull Down for Virtual Try-On" else "Virtual $title glasses Try-On coming soon!"
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                header,
                style = MaterialTheme.typography.caption,
                fontSize = 18.sp,
                color = Color.White,
                textAlign = TextAlign.Center
            )
            if (bottomSheetState.isCollapsed) {
                Text(
                    "For demo purposes, use these awesome yellow glasses",
                    style = MaterialTheme.typography.caption,
                    fontSize = 18.sp,
                    color = Color.White,
                    textAlign = TextAlign.Center
                )
            }
        }
        Spacer(
            Modifier
                .fillMaxWidth()
                .height(12.dp)
        )
        Box(
            Modifier
                .clip(MaterialTheme.shapes.medium)
                .fillMaxSize()
                .background(Color.White),
            content = content
        )
    }
}