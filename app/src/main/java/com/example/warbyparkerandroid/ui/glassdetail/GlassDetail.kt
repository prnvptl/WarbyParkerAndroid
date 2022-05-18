package com.example.warbyparkerandroid.ui.glassdetail

import android.widget.Toast
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.warbyparkerandroid.R
import com.example.warbyparkerandroid.data.model.GlassStyle
import com.example.warbyparkerandroid.data.model.Glasses
import com.example.warbyparkerandroid.ui.common.CloseButton
import com.example.warbyparkerandroid.ui.common.ColorStyledButtonList
import com.example.warbyparkerandroid.ui.common.FavoriteButton
import com.example.warbyparkerandroid.ui.common.FloatingActionButtons
import com.example.warbyparkerandroid.ui.glasses.CollapsableHandle
import com.example.warbyparkerandroid.ui.glasses.EyeGlassesViewModel
import com.example.warbyparkerandroid.ui.theme.WarbyParkerAndroidTheme
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch
import kotlin.math.ceil

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun GlassDetail(
    glass: Glasses,
    selectedStyle: GlassStyle,
    viewModel: EyeGlassesViewModel,
    onNavBackPressed: () -> Unit
) {
    val context = LocalContext.current
    val initialStyle = glass.styles.find { selectedStyle.id == it.id }
    var style by remember { mutableStateOf(initialStyle) }
    val coroutineScope = rememberCoroutineScope()
    val state = rememberLazyListState()
    val firstItemVisible by remember {
        derivedStateOf {
            state.firstVisibleItemIndex == 0
        }
    }
    CompositionLocalProvider(LocalElevationOverlay provides null) {
        WarbyParkerAndroidTheme {
            var bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
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
                backgroundColor = Color.Transparent,
                sheetContent = {

                    Box(modifier = Modifier.fillMaxSize()) {
                        LazyColumn(state = state) {
                            item {
                                HeaderContainer(
                                    title = glass.brand,
                                    bottomSheetState = bottomSheetScaffoldState.bottomSheetState
                                )
                            }
                            item {

                                Column(
                                    Modifier
                                        .clip(MaterialTheme.shapes.medium)
                                        .fillMaxSize()
                                        .background(Color.White),
                                    verticalArrangement = Arrangement.SpaceBetween
                                ) {

                                    DetailHeader(selectedImagesIndicator = pagerSelection) {
                                        onNavBackPressed()
                                    }

                                    HorizontalGlassScroller(glassImages = glass.imageIds) { page ->
                                        pagerSelection.clear()
                                        val newItems = listOf(page == 0, page == 1, page == 2)
                                        pagerSelection.addAll(newItems)
                                    }
                                    ColorStyledButtonList(
                                        selectedStyle = style!!,
                                        styles = glass.styles,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(top = 20.dp)
                                            .wrapContentSize(Alignment.Center),
                                    ) { onSelectStyle ->
                                        val newStyle =
                                            glass.styles.find { it.id == onSelectStyle.id }
                                        if (newStyle != null) {
                                            style = newStyle
                                        }
                                        Toast.makeText(
                                            context,
                                            "Other style images coming soon!",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                    GlassNameContainer(
                                        brand = glass.brand,
                                        style = selectedStyle,
                                        modifier = Modifier.padding(
                                            start = 14.dp, end = 14.dp, top = 30.dp
                                        )
                                    ) {
                                        viewModel.update(it)
                                    }
                                    Divider(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(14.dp)
                                            .clip(RoundedCornerShape(4.dp)),
                                        thickness = 2.dp,
                                        color = MaterialTheme.colors.onBackground
                                    )
                                    PriceDescription(selectedStyle.price.toInt())
                                    BasicDescription(glass)
                                    VirtualTryOnSection(glass) {
                                        coroutineScope.launch {
                                            bottomSheetScaffoldState.bottomSheetState.collapse()
                                        }

                                    }
                                    WidthDescription(glass)
                                    PrescriptionDescription(glass)
                                    WhatsIncluded(glass)

                                }
                            }
                        }
                        androidx.compose.animation.AnimatedVisibility(
                            visible = !firstItemVisible && bottomSheetScaffoldState.bottomSheetState.isExpanded,
                            enter = fadeIn(),
                            exit = fadeOut()
                        ) {
                            CenterAlignedTopAppBar(title = {
                                Text(buildAnnotatedString {
                                    withStyle(style = SpanStyle(fontSize = 20.sp)) {
                                        append("${glass.brand}\n")
                                    }
                                    style?.let { append(it.name) }
                                }, textAlign = TextAlign.Center)

                            }, navigationIcon = {
                                CloseButton(modifier = Modifier.padding(start = 8.dp)) {
                                    onNavBackPressed()
                                }
                            }, colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                                containerColor = Color.White
                            ), actions = {
                                IconButton(
                                    onClick = {
                                        coroutineScope.launch {
                                            bottomSheetScaffoldState.bottomSheetState.collapse()
                                            state.animateScrollToItem(0)
                                        }

                                    }, modifier = Modifier.padding(end = 12.dp)
                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_baseline_camera_front_24),
                                        contentDescription = null,
                                        tint = MaterialTheme.colors.primaryVariant
                                    )
                                }
                            }, modifier = Modifier.align(Alignment.TopCenter)
                            )
                        }
                        FloatingActionButtons(
                            modifier = Modifier.align(Alignment.BottomCenter).padding(bottom = 40.dp),
                            buttonOne = "Home Try-On",
                            ButtonOneIcon = {
                                Icon(
                                    imageVector = Icons.Filled.Add,
                                    contentDescription = null,
                                    Modifier.size(12.dp)
                                )
                            },
                            buttonTwo = "Buy from $${selectedStyle.price.toInt()}",
                            buttonOneOnClick = {},
                            buttonTwoOnClick = {},
                        )
                    }
                },
            ) {}
        }
    }
}

@Composable
fun WhatsIncluded(glass: Glasses) {
    Column(modifier = Modifier.background(Color(0xFFf8f7f9))) {
        Image(
            painter = painterResource(id = glass.whatsIncludedImg),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 4.dp),
            contentScale = ContentScale.FillWidth
        )
        Text(
            text = "Everything that's included",
            modifier = Modifier.padding(horizontal = 15.dp, vertical = 20.dp),
            fontSize = 30.sp,
            fontStyle = FontStyle.Normal,
            fontWeight = FontWeight.Normal,
            fontFamily = FontFamily.Serif
        )
        Text(
            text = "Each pair of glasses comes with prescription lenses, plus a frame case and lens cloth. We also offer free shipping and a 30-day, hassle-free return or exchange policy as well as a six-month, no scratch guarantee for our prescription lenses; for prescription orders, we'll replace your scratched lenses with a new pair of classes for free within six months of purchase.",
            fontSize = 22.sp,
            fontFamily = FontFamily.Serif,
            modifier = Modifier.padding(horizontal = 15.dp),
            style = MaterialTheme.typography.caption,
            color = Color.Gray,
            fontWeight = FontWeight.ExtraLight,
        )
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
        )
    }
}

@Composable
fun PrescriptionDescription(glass: Glasses) {
    Column {
        Image(
            painter = painterResource(id = glass.perscriptionImg),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 4.dp),
            contentScale = ContentScale.FillWidth
        )
        Text(
            text = "We offer a variety of prescription and lens types",
            modifier = Modifier.padding(horizontal = 15.dp, vertical = 20.dp),
            fontSize = 30.sp,
            fontStyle = FontStyle.Normal,
            fontWeight = FontWeight.Normal,
            fontFamily = FontFamily.Serif
        )
        PrescriptionTypeDescription(
            type = "Prescription \ntype", options = listOf(
                "Single-vision", "Progressives", "Anti-fatigue", "Readers", "Non-prescription"
            )
        )
        PrescriptionTypeDescription(
            type = "Lens type", options = listOf(
                "Classic", "Blue-light-filtering", "Light-responsive"
            )
        )
        PrescriptionTypeDescription(
            type = "Lens material", options = listOf(
                "Polycarbonate", "1.67 high-index"
            )
        )
        DetailsDivider()
    }
}

@Composable
fun PrescriptionTypeDescription(type: String, options: List<String>) {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 15.dp, vertical = 15.dp)
    ) {
        Text(
            type, textAlign = TextAlign.Left,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1F)
                .padding(top = 12.dp),
            fontSize = 24.sp,
            fontStyle = FontStyle.Normal,
            fontWeight = FontWeight.SemiBold,
            fontFamily = FontFamily.Serif,

            )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1F)
        ) {
            options.forEach {
                DescriptionBullet(text = it)
            }
        }
    }
}

@Composable
fun WidthDescription(glass: Glasses) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFf8f7f9))
    ) {
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp)
        )
        Image(
            painter = painterResource(id = glass.multiWidthImg),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 10.dp)
                .padding(bottom = 4.dp),
            contentScale = ContentScale.FillWidth
        )
        Text(
            "From top to bottom: extra wide, wide, medium, extra narrow",
            fontStyle = FontStyle.Italic,
            fontWeight = FontWeight.Light,
            modifier = Modifier.padding(horizontal = 15.dp),
            fontSize = 16.sp
        )
        Text(
            text = "${glass.brand} is available in extra narrow, narrow, medium, wide, and extra wide",
            modifier = Modifier.padding(horizontal = 15.dp, vertical = 20.dp),
            fontSize = 30.sp,
            fontStyle = FontStyle.Normal,
            fontWeight = FontWeight.Normal,
            fontFamily = FontFamily.Serif
        )
        Text(
            text = "Choose a width that corresponds with your face width, e.g., pick a narrow if you have a narrow face (and so on). If you're still unsure, select a few different sized in a free Home Try-On.",
            fontSize = 22.sp,
            fontFamily = FontFamily.Serif,
            modifier = Modifier.padding(horizontal = 15.dp),
            style = MaterialTheme.typography.caption,
            color = Color.Gray,
            fontWeight = FontWeight.ExtraLight,
        )
        Button(
            onClick = { },
            shape = MaterialTheme.shapes.large,
            modifier = Modifier
                .height(76.dp)
                .width(250.dp)
                .align(Alignment.CenterHorizontally)
                .padding(top = 24.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
        ) {
            Text("Find your width", color = Color.Black, fontSize = 18.sp)
        }

        Text(
            "View Measurements",
            color = MaterialTheme.colors.primaryVariant,
            fontSize = 20.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier
                .padding(top = 40.dp)
                .align(Alignment.CenterHorizontally)
        )
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp)
        )
        DetailsDivider()
    }
}

@Composable
fun DetailsDivider() {
    Divider(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(4.dp)),
        thickness = 2.dp,
        color = MaterialTheme.colors.onBackground
    )
}

@Composable
fun VirtualTryOnSection(glass: Glasses, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Transparent),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = glass.virtualTryOnImg),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 35.dp, horizontal = 35.dp),
            contentScale = ContentScale.FillWidth
        )
        Text(
            text = "Use your camera to try on frames right now",
            modifier = Modifier
                .padding(horizontal = 15.dp)
                .padding(bottom = 12.dp),
            fontSize = 36.sp,
            fontStyle = FontStyle.Normal,
            fontWeight = FontWeight.Light,
            textAlign = TextAlign.Center
        )
        Button(
            onClick = { onClick() },
            shape = MaterialTheme.shapes.large,
            modifier = Modifier
                .height(56.dp)
                .width(250.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
        ) {
            Text("Access Virtual Try-On", color = Color.Black, fontSize = 18.sp)
        }
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp)
        )
        DetailsDivider()
    }
}

@Composable
fun BasicDescription(glass: Glasses) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 35.dp)
            .background(Color(0xFFf8f7f9))
    ) {
        Image(
            painter = painterResource(id = glass.descriptionImg),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 10.dp)
                .padding(bottom = 12.dp),
            contentScale = ContentScale.FillWidth
        )
        Text(
            text = glass.description,
            modifier = Modifier
                .padding(horizontal = 15.dp)
                .padding(bottom = 12.dp),
            fontSize = 36.sp,
            fontStyle = FontStyle.Normal,
            fontWeight = FontWeight.Light
        )
        DescriptionBullet("Made from hand-polished cellulose acetate")
        DescriptionBullet("Aukolon-coated screws for durability")
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp)
        )
        DetailsDivider()
    }
}

@Composable
fun DescriptionBullet(text: String) {
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .padding(horizontal = 15.dp)
            .padding(bottom = 14.dp, top = 14.dp)
            .wrapContentSize(Alignment.Center)
    ) {
        Canvas(modifier = Modifier
            .size(13.dp)
            .align(Alignment.CenterVertically)
            .padding(end = 6.dp),
            onDraw = {
                drawCircle(color = Color.Gray)
            })
        Text(
            text = text,
            textAlign = TextAlign.Center,
            fontSize = 20.sp,
            fontWeight = FontWeight.Light,
            modifier = Modifier.padding(start = 6.dp)
        )
    }
}

@Composable
fun PriceDescription(price: Int) {
    Text(buildAnnotatedString {
        append("Starting at $$price, including prescription lenses")
        withStyle(
            style = SpanStyle(
                color = MaterialTheme.colors.primaryVariant, fontWeight = FontWeight.Bold
            )
        ) {
            append(" or 4 payements of $${ceil(price / 4.0).toInt()} with Affirm")
        }
    }, modifier = Modifier.padding(14.dp), fontSize = 20.sp)
}

@Composable
fun GlassNameContainer(
    brand: String,
    style: GlassStyle,
    modifier: Modifier,
    onFavoriteClick: (style: GlassStyle) -> Unit
) {
    var isFav by remember {
        mutableStateOf(style.isFavorite)
    }
    Row(
        modifier = modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(brand, textAlign = TextAlign.Center, style = MaterialTheme.typography.h4)
            Text(
                style.name,
                fontStyle = FontStyle.Italic,
                style = MaterialTheme.typography.overline,
                fontSize = 20.sp,
                fontFamily = FontFamily.Serif
            )
        }
        FavoriteButton(style = style, isFavorite = isFav, modifier = Modifier) {
            val copy = style.copy()
            copy.isFavorite = !isFav
            isFav = copy.isFavorite
            onFavoriteClick(copy)
        }
    }
}

@Composable
fun DetailHeader(selectedImagesIndicator: List<Boolean>, onBack: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 18.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        CloseButton(onSelect = onBack)
        LazyRow(
            contentPadding = PaddingValues(
                top = 18.dp, end = 8.dp
            )
        ) {
            items(selectedImagesIndicator) {
                val indicatorColor = if (it) Color.Gray else MaterialTheme.colors.onBackground
                Canvas(modifier = Modifier
                    .size(13.dp)
                    .padding(end = 6.dp), onDraw = {
                    drawCircle(color = indicatorColor)
                })
            }
        }
    }
}


@OptIn(ExperimentalPagerApi::class)
@Composable
fun HorizontalGlassScroller(glassImages: List<Int>, onPageSelected: (page: Int) -> Unit) {
    val pagerState = rememberPagerState()
    LaunchedEffect(pagerState) {
        // Collect from the pager state a snapshotFlow reading the currentPage
        snapshotFlow { pagerState.currentPage }.collect { page ->
            onPageSelected(page)
        }
    }
    val configuration = LocalConfiguration.current

    HorizontalPager(
        count = 3,
        state = pagerState,
        modifier = Modifier
            .fillMaxWidth()
            .height((configuration.screenHeightDp / 2.5).dp)
            .background(Color.Blue)
    ) { page ->
        // Our page content
        Image(
            painter = painterResource(id = glassImages[page]),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillWidth
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HeaderContainer(
    title: String,
    bottomSheetState: BottomSheetState,
//    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.primaryVariant),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        if (bottomSheetState.isCollapsed) {
            CollapsableHandle()
        }
        val spacerPadding = if (bottomSheetState.isExpanded) 60.dp else 10.dp
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
//        Column(
//            Modifier
//                .clip(MaterialTheme.shapes.medium)
//                .fillMaxSize()
//                .background(Color.White),
//            content = content,
//            verticalArrangement = Arrangement.SpaceBetween
//        )
    }
}