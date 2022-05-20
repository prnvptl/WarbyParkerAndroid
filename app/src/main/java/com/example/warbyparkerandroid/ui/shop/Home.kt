package com.example.warbyparkerandroid.ui.shop

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.warbyparkerandroid.R
import com.example.warbyparkerandroid.ui.theme.WarbyParkerAndroidTheme

@Composable
fun HomeScreen(
    onEyeglassesShopClick: () -> Unit,
    onContactsClick: () -> Unit,
) {
    val shopItems = listOf(
        ShopItem(R.drawable.trialglasses, false, onEyeglassesShopClick),
        ShopItem(R.drawable.eyeglasses_header, false, onEyeglassesShopClick),
        ShopItem(R.drawable.sunglasses, false, onEyeglassesShopClick),
        ShopItem(R.drawable.lenses, true, onContactsClick),
        ShopItem(R.drawable.accessories, true, onEyeglassesShopClick),
        ShopItem(R.drawable.giftcards, true, onEyeglassesShopClick)
    )

    val state = rememberLazyListState()
    LazyColumn(state = state) {
        items(shopItems, key = { it.imageId }) {
            ShopItemCard(item = it, isScrolling = state.isScrollInProgress)
        }
    }
}

@Composable
fun ShopItemCard(item: ShopItem, isScrolling: Boolean) {
    var checkedState by rememberSaveable { mutableStateOf(false) }
    val shouldBeChecked = checkedState && !isScrolling && !item.isUnisex
    if (isScrolling) {
        checkedState = false
    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
                checkedState = !checkedState
                if(item.isUnisex) {
                    item.onShopClick()
                }
            }
            .wrapContentSize(align = Alignment.Center)
            .clip(MaterialTheme.shapes.medium),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(item.imageId), contentDescription = null,
            contentScale = ContentScale.FillWidth,
            modifier = Modifier.fillMaxWidth(),
            colorFilter = if (shouldBeChecked) ColorFilter.colorMatrix(
                ColorMatrix().apply {
                    setToScale(.4F, .46F, .45F, .1F)
                }
            ) else null
        )
        if (shouldBeChecked) {
            Row(modifier = Modifier.padding(10.dp)) {
                Button(
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
                    modifier = Modifier
                        .padding(10.dp)
                        .width(175.dp)
                        .clip(MaterialTheme.shapes.large),
                    onClick = { item.onShopClick() }) {
                    Text(text = "Shop Men")
                }
                Button(
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
                    modifier = Modifier
                        .padding(10.dp)
                        .width(175.dp)
                        .clip(MaterialTheme.shapes.large),
                    onClick = { item.onShopClick() }) {
                    Text(text = "Shop Women")
                }
            }
        }
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    WarbyParkerAndroidTheme {
        HomeScreen({}, {})
    }
}