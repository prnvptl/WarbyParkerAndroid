package com.example.warbyparkerandroid.ui.favorites

import android.annotation.SuppressLint
import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.warbyparkerandroid.R
import com.example.warbyparkerandroid.data.model.GlassStyle
import com.example.warbyparkerandroid.data.model.Glasses
import com.example.warbyparkerandroid.ui.common.CloseButton
import com.example.warbyparkerandroid.ui.common.GlassesList
import com.example.warbyparkerandroid.ui.glasses.CollapsableHandle
import com.example.warbyparkerandroid.ui.glasses.EyeGlassesViewModel
import com.example.warbyparkerandroid.ui.login.SignIn
import com.example.warbyparkerandroid.ui.virtualtryon.AugmentedFaceActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun Favorites(
    viewModel: EyeGlassesViewModel, hideBottomNav: () -> Unit,
    showBottomNav: () -> Unit, onShopClicked: () -> Unit
) {
    val viewModelUiState by viewModel.uiState.collectAsState()
    if (viewModel.getFavoritesCount() <= 0) {
        FavoriteEmptyState(hideBottomNav, showBottomNav, onShopClicked)
    } else {
        FavoritesContent(glasses = viewModelUiState.eyeGlasses, viewModel)

    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun FavoritesContent(glasses: List<Glasses>, viewModel: EyeGlassesViewModel) {
    val favorites = mutableListOf<Glasses>()
    val favPredicate: (GlassStyle) -> Boolean = { it.isFavorite }
    glasses.forEach { glass ->
        val favs = glass.styles.filter(favPredicate)
        if (favs.isNotEmpty()) {
            val copy = glass.copy()
            copy.styles = ArrayList(favs)
            favorites.add(copy)
        }
    }
    val context = LocalContext.current
    val state = rememberLazyListState()
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = "Favorites", style = MaterialTheme.typography.h5) },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.White
                ),
            )
        }
    ) {
        val intent = Intent(context, AugmentedFaceActivity::class.java)
//        intent.putExtra("view_model", viewModel)
        GlassesList(
            state,
            favorites,
            showHeader = false,
            onUpdateStyle = { viewModel.update(it) },
            onGlassSelect = { intent })
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun FavoriteEmptyState(
    hideBottomNav: () -> Unit,
    showBottomNav: () -> Unit, onShopClicked: () -> Unit
) {
    val modalState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden,
        confirmStateChange = {
            if (it == ModalBottomSheetValue.Hidden) {
                showBottomNav()
            }
            it != ModalBottomSheetValue.HalfExpanded
        })
    val scope = rememberCoroutineScope()
    ModalBottomSheetLayout(
        sheetState = modalState, sheetElevation = 8.dp, sheetContent = {
            CollapsableHandle()
            CloseButton(modifier = Modifier.padding(top = 12.dp, start = 12.dp)) {
                scope.launch {
                    modalState.animateTo(ModalBottomSheetValue.Hidden)
                    showBottomNav()
                }
            }
            SignIn()
        }, sheetBackgroundColor = Color(0xFFf8f7f9)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .wrapContentSize(),
            verticalArrangement = Arrangement.spacedBy(15.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                "No favorites to be found",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.h5,
                fontWeight = FontWeight.SemiBold
            )
            Text("(Click below to change that.)", textAlign = TextAlign.Center, fontSize = 18.sp)
            Image(
                painter = painterResource(id = R.drawable.no_favorites_img),
                contentDescription = null,
                modifier = Modifier.padding(vertical = 40.dp)
            )
            Button(
                onClick = { onShopClicked() },
                shape = MaterialTheme.shapes.large,
                modifier = Modifier
                    .width(175.dp)
                    .height(40.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primaryVariant)
            ) {
                Text("Start Shopping", color = Color.White)
            }
            val caption = buildAnnotatedString {
                append("Already have favorites? ")
                withStyle(
                    style = SpanStyle(
                        color = MaterialTheme.colors.primaryVariant,
                        fontWeight = FontWeight.SemiBold
                    )
                ) {
                    append("Sign In.")
                }
            }
            Text(caption, fontSize = 20.sp, modifier = Modifier.clickable {
                scope.launch {
                    hideBottomNav()
                    delay(300)
                    modalState.animateTo(ModalBottomSheetValue.Expanded)
                }
            })
        }
    }
}