package com.example.warbyparkerandroid.ui.common

import android.content.Intent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.warbyparkerandroid.R
import com.example.warbyparkerandroid.data.model.GlassStyle
import com.example.warbyparkerandroid.data.model.Glasses

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun GlassesList(
    state: LazyListState,
    glasses: List<Glasses>,
    onUpdateStyle: (style: GlassStyle) -> Unit,
    showHeader: Boolean = true,
    onGlassSelect: () -> Intent
) {
    LazyColumn(
        state = state,
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        contentPadding = PaddingValues(8.dp),
    ) {
        if (showHeader) {
            item {
                Text(
                    text = "Eyeglasses",
                    style = MaterialTheme.typography.h5,
                    modifier = Modifier.padding(10.dp)
                )
            }
        }
        items(glasses,
            key = { glass ->
                glass.id
            }) {
            GlassesItem(
                glasses = it,
                modifier = Modifier.animateItemPlacement(),
                onFavoriteClick = { style -> onUpdateStyle(style) }) {
                onGlassSelect()
            }

        }
    }
}


@Composable
fun GlassesItem(
    glasses: Glasses,
    onFavoriteClick: (style: GlassStyle) -> Unit,
    modifier: Modifier = Modifier,
    onGlassSelect: () -> Intent
) {
    val context = LocalContext.current

    var selectedStyle by remember { mutableStateOf(glasses.styles[0]) }

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
                .height(146.dp)
                .fillMaxWidth(),
            contentScale = ContentScale.FillWidth
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