package com.example.warbyparkerandroid.ui.glasses

import android.content.Intent
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.rememberLazyListState
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.warbyparkerandroid.R
import com.example.warbyparkerandroid.data.model.GlassStyle
import com.example.warbyparkerandroid.data.model.Glasses
import com.example.warbyparkerandroid.ui.common.ColorStyledButtonList
import com.example.warbyparkerandroid.ui.common.FavoriteButton
import com.example.warbyparkerandroid.ui.common.GlassesList
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
    SideEffect {
        Log.i("Glasses~~! ", "favState.toString()")
        viewModel.updateCount()
    }
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


            Log.i("padding", "$it")
            val intent = Intent(context, AugmentedFaceActivity::class.java)
            intent.putExtra("view_model", viewModel)
            GlassesList(
                state,
                glasses,
                onUpdateStyle = { viewModel.update(it) },
                onGlassSelect = { intent })
        }
    }
}

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

