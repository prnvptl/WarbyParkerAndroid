package com.example.warbyparkerandroid.ui.glasses

import android.annotation.SuppressLint
import android.content.Intent
import android.util.Log
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.warbyparkerandroid.R
import com.example.warbyparkerandroid.ui.common.GlassesList
import com.example.warbyparkerandroid.ui.virtualtryon.AugmentedFaceActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun Glasses(
    onBack: () -> Unit,
    hideBottomNav: () -> Unit,
    showBottomNav: () -> Unit,
    viewModel: EyeGlassesViewModel
) {

    val viewModelUiState by viewModel.uiState.collectAsState()

    val context = LocalContext.current
    var searchState by remember {
        mutableStateOf(false)
    }
    var searchTerm by remember {
        mutableStateOf("")
    }
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
                if (searchState) {
                    SearchTopBar(searchTerm, onSearch = {
                        searchTerm = it
                        viewModel.search(searchTerm)
                    }) {
                        searchState = false
                        searchTerm = ""
                        viewModel.search("")
                    }
                } else {
                    CenterTopAppBar(
                        firstItemVisible,
                        onBack = onBack,
                        onSearchClick = { searchState = true }) {
                        hideBottomNav()
                        scope.launch {
                            delay(300)
                            modalState.animateTo(ModalBottomSheetValue.Expanded)
                        }
                    }
                }
            },
        ) {
            when (viewModelUiState) {
                is GlassesUiState.NoGlasses -> {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .fillMaxSize()
                            .wrapContentSize(Alignment.Center)
                    )
                }
            }
            val intent = Intent(context, AugmentedFaceActivity::class.java)
//            intent.putExtra("view_model", viewModel)

            AnimatedVisibility(
                visible = viewModelUiState is GlassesUiState.HasGlasses,
                enter = slideInVertically(initialOffsetY = { 3000 }) + fadeIn(tween(3000)),
                exit = shrinkVertically()
            ) {
                Box(
                ) {
                    Log.i("SUCCESS", viewModelUiState.eyeGlasses.size.toString())
                    GlassesList(
                        state,
                        viewModelUiState.eyeGlasses,
                        onUpdateStyle = { viewModel.update(it) },
                        onGlassSelect = { intent })
                    if (searchState && searchTerm.isBlank()) {
                        Surface(
                            modifier = Modifier.fillMaxSize(),
                            color = Color.White.copy(alpha = .9f)
                        ) {}
                    }
                }
            }
        }
    }
}

@Composable
fun CenterTopAppBar(
    isFirstItemVisible: Boolean,
    onBack: () -> Unit,
    onSearchClick: () -> Unit,
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
            IconButton(onClick = { onSearchClick() }) {
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

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchTopBar(term: String, onSearch: (term: String) -> Unit, onCancel: () -> Unit) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusRequester = remember { FocusRequester() }
    TopAppBar(
        title = {
            Text("")
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(focusRequester),
                value = term,
                onValueChange = { onSearch(it) },
                placeholder = {
                    Text(text = "Search Eyeglasses")
                },
                colors = TextFieldDefaults.textFieldColors(
                    focusedIndicatorColor = Color.Transparent,
                    cursorColor = MaterialTheme.colors.primaryVariant,
                    placeholderColor = Color.Gray
                ),
                maxLines = 1,
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = androidx.compose.ui.text.input.ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = {
                    onSearch(term)
                    keyboardController?.hide()
                }),
            )
        },
        navigationIcon = {
            IconButton(onClick = { }) {
                Icon(Icons.Filled.Search, contentDescription = null, tint = Color.LightGray)
            }
        },
        actions = {
            Text("Cancel",
                style = MaterialTheme.typography.button,
                color = MaterialTheme.colors.primaryVariant,
                fontSize = 18.sp,
                modifier = Modifier
                    .padding(end = 14.dp)
                    .clickable {
                        onCancel()
                    })
        },
    )
    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }
}

