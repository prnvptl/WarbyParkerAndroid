package com.example.warbyparkerandroid.ui.glasses

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.warbyparkerandroid.R
import com.example.warbyparkerandroid.data.model.GlassStyle
import com.example.warbyparkerandroid.data.model.Glasses
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun Glasses(
    onBack: () -> Unit,
    hideBottomNav: () -> Unit,
    showBottomNav: () -> Unit,
    viewModel: EyeGlassesViewModel = viewModel()
) {
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
//    val glasses by viewModel.eyeGlasses
//    DisposableEffect(key1 = viewModel) {
////        onDispose { viewModel.clearAnimation() }
//    }
    val glasses by viewModel.eyeGlasses.observeAsState(initial = emptyList())
    ModalBottomSheetLayout(
        sheetState = modalState,
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
            GlassesList(state, glasses) {
                viewModel.update(it)
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun GlassesList(
    state: LazyListState,
    glasses: List<Glasses>,
    onUpdateStyle: (style: GlassStyle) -> Unit
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
                glass.brand
            }) {
            AnimatedVisibility(
                it.visible,
                enter = slideInVertically(initialOffsetY = { 300 }) + fadeIn(tween(1500)),
                exit = shrinkVertically()
            ) {
                GlassesItem(glasses = it) { style -> onUpdateStyle(style) }
            }
        }
    }
}

@Composable
fun GlassesItem(
    glasses: Glasses,
    onFavoriteClick: (style: GlassStyle) -> Unit
) {
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
            FavoriteButton(style = selectedStyle, modifier = Modifier.align(Alignment.TopEnd)) {
                val copy = selectedStyle.copy()
                copy.isFavorite = !copy.isFavorite
                selectedStyle = copy
                onFavoriteClick(copy)
            }
        }
    }
}

@Composable
fun FavoriteButton(style: GlassStyle, modifier: Modifier, onSelect: () -> Unit) {
    IconButton(onClick = { onSelect() }, modifier = modifier) {
        Crossfade(targetState = style.isFavorite) {
            if (it) {
                Icon(
                    painterResource(id = R.drawable.ic_baseline_favorite_24),
                    contentDescription = null,
                    tint = Color.Red
                )
            } else {
                Icon(
                    painterResource(id = R.drawable.ic_baseline_favorite_border_24),
                    contentDescription = null,
                    tint = Color.LightGray
                )
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
    Glasses({}, {}, {})
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

