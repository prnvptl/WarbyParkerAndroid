package com.example.warbyparkerandroid.ui.glasses

import android.content.Intent
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.warbyparkerandroid.R
import com.example.warbyparkerandroid.data.model.GlassStyle
import com.example.warbyparkerandroid.data.model.Glasses
import com.example.warbyparkerandroid.ui.common.ColorStyledButtonList
import com.example.warbyparkerandroid.ui.common.FavoriteButton
import com.example.warbyparkerandroid.ui.virtualtryon.AugmentedFaceActivity
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun Glasses(
    onBack: () -> Unit,
    hideBottomNav: () -> Unit,
    showBottomNav: () -> Unit,
    viewModel: EyeGlassesViewModel
) {
    val context = LocalContext.current

    val state = rememberLazyListState()
    val firstItemVisible by remember {
        derivedStateOf {
            state.firstVisibleItemIndex == 0
        }
    }
    val modalState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmStateChange = {
            if (it == ModalBottomSheetValue.Hidden) {
                showBottomNav()
            }
            it != ModalBottomSheetValue.HalfExpanded
        }
    )
    val scope = rememberCoroutineScope()
    val glasses by viewModel.eyeGlasses.observeAsState(initial = emptyList())
    ModalBottomSheetLayout(
        sheetState = modalState,
        sheetElevation = 8.dp,
        sheetContent = {
            FiltersContent {
                showBottomNav()
                scope.launch { modalState.animateTo(ModalBottomSheetValue.Hidden) }
            }
        },
    ) {
        Scaffold(
            topBar = {
                CenterTopAppBar(firstItemVisible, onBack = onBack) {
                    hideBottomNav()
                    scope.launch { modalState.animateTo(ModalBottomSheetValue.Expanded) }
                }
            }
        ) {

            val intent = Intent(context, AugmentedFaceActivity::class.java)
//            intent.putExtra("glass", it)
//            context.startActivity(intent)
            intent.putExtra("view_model", viewModel)
            GlassesList(
                state,
                glasses,
                onUpdateStyle = { viewModel.update(it) },
                onGlassSelect = { intent })
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun GlassesList(
    state: LazyListState,
    glasses: List<Glasses>,
    onUpdateStyle: (style: GlassStyle) -> Unit,
    onGlassSelect: () -> Intent
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
        items(glasses,
            key = { glass ->
                glass.id
            }) {
            AnimatedVisibility(
                it.visible,
                enter = slideInVertically(initialOffsetY = { 300 }) + fadeIn(tween(1500)),
                exit = shrinkVertically()
            ) {
                GlassesItem(glasses = it, onFavoriteClick = { style -> onUpdateStyle(style) }) {
                    onGlassSelect()
                }
            }
        }
    }
}

@Composable
fun GlassesItem(
    glasses: Glasses,
    onFavoriteClick: (style: GlassStyle) -> Unit,
    onGlassSelect: () -> Intent
) {
    val context = LocalContext.current

    var selectedStyle by remember { mutableStateOf(glasses.styles[0]) }
    SideEffect {
        onFavoriteClick(selectedStyle)
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                val intent = onGlassSelect()
                intent.putExtra("glass", glasses)
                intent.putExtra("glass_style", selectedStyle)
                context.startActivity(intent)
            },
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
            fontFamily = FontFamily.Serif,
            modifier = Modifier.fillMaxWidth()
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)
        ) {
            ColorStyledButtonList(
                selectedStyle = selectedStyle, styles = glasses.styles, modifier = Modifier
                    .align(Alignment.Center)
            ) {
                selectedStyle = it
            }
            FavoriteButton(style = selectedStyle, modifier = Modifier.align(Alignment.TopEnd)) {
                val copy = selectedStyle.copy()
                copy.isFavorite = !copy.isFavorite
                selectedStyle = copy
                onFavoriteClick(copy)
            }
        }
    }
}

//@Composable
//fun FavoriteButton(style: GlassStyle, modifier: Modifier, onSelect: () -> Unit) {
//    IconButton(onClick = { onSelect() }, modifier = modifier) {
//        Crossfade(targetState = style.isFavorite) {
//            if (it) {
//                Icon(
//                    painterResource(id = R.drawable.ic_baseline_favorite_24),
//                    contentDescription = null,
//                    tint = Color.Red
//                )
//            } else {
//                Icon(
//                    painterResource(id = R.drawable.ic_baseline_favorite_border_24),
//                    contentDescription = null,
//                    tint = Color.LightGray
//                )
//            }
//        }
//    }
//}

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
//    Glasses({}, {}, {})
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CenterTopAppBar(
    isFirstItemVisible: Boolean,
    onBack: () -> Unit,
    onFiltersClick: () -> Unit,
) {
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
                    fontFamily = FontFamily.Serif,
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
            IconButton(onClick = { onFiltersClick() }) {
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

