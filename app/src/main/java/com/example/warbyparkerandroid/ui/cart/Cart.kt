package com.example.warbyparkerandroid.ui.cart

import android.annotation.SuppressLint
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.warbyparkerandroid.R
import com.example.warbyparkerandroid.data.model.CartItem
import com.example.warbyparkerandroid.ui.common.CloseButton
import com.example.warbyparkerandroid.ui.common.FloatingActionButtons
import java.lang.Math.ceil

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun Cart(
    viewModel: CartViewModel = viewModel(),
    onBack: () -> Unit
) {
    val shopItems = viewModel.cartItems.observeAsState(initial = emptyList()).value
    var totalPrice = 0F
    if (shopItems.isNotEmpty()) {
        totalPrice = shopItems.map { it.style.price }.reduce { sum, curr -> sum + curr }
    }
    val state = rememberLazyListState()
    val firstItemVisible by remember {
        derivedStateOf {
            state.firstVisibleItemIndex == 0
        }
    }
    val transition = updateTransition(targetState = firstItemVisible)
    val color by transition.animateColor { itemVisible ->
        if (itemVisible) Color(0xFFf8f7f9) else Color.White
    }
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    AnimatedVisibility(
                        visible = !firstItemVisible,
                        enter = fadeIn(),
                        exit = fadeOut()
                    ) {
                        Column(
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                "Your Cart: $${totalPrice?.toInt()}",
                                fontWeight = FontWeight.SemiBold
                            )
                            Text("${shopItems.size} items")
                        }
                    }
                },
                modifier = Modifier.background(Color(0xFFf8f7f9)),
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = color
                ),
                navigationIcon = {
                    CloseButton(modifier = Modifier.padding(top = 12.dp, start = 12.dp)) {
                        onBack()
                    }
                }
            )
        },
        modifier = Modifier.background(Color(0xFFf8f7f9))
    ) { it ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFf8f7f9))
                .padding(it)
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFFf8f7f9)),
                verticalArrangement = Arrangement.spacedBy(20.dp),
                state = state
            ) {
                item {
                    Text(
                        "Your Cart: $145", modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 14.dp)
                            .padding(horizontal = 30.dp),
                        textAlign = TextAlign.Center,
                        fontSize = 24.sp,
                        fontStyle = FontStyle.Normal,
                        fontWeight = FontWeight.Normal,
                        fontFamily = FontFamily.Serif
                    )
                }
                items(shopItems) { item ->
                    AnimatedVisibility(
                        visibleState = item.visible,
                        enter = slideInVertically { 300 } + fadeIn(tween(400)),
                        exit = slideOutVertically { 300 } + fadeOut(tween(400))
                    ) {
                        Column {
                            CartItem(item = item)
                            Footer(item)
                        }
                    }
                }

                item {
                    if (shopItems.isNotEmpty()) {
                        AnimatedVisibility(visibleState = shopItems[0].visible) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 6.dp)
                                    .padding(horizontal = 30.dp)
                                    .padding(top = 12.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    "Shipping",
                                    fontSize = 18.sp,
                                    style = MaterialTheme.typography.button
                                )
                                Text("Free", color = Color.Gray)
                            }
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 30.dp)
                                    .padding(vertical = 6.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    "Subtotal",
                                    fontSize = 18.sp,
                                    style = MaterialTheme.typography.button
                                )
                                Text("$${totalPrice?.toInt()}", color = Color.Gray)
                            }
                        }
                    }

                }
                item {
                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(80.dp)
                    )
                }
            }
            FloatingActionButtons(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 20.dp),
                buttonOne = "Pay",
                ButtonOneIcon = {
                    Image(
                        painter = painterResource(id = R.drawable.g),
                        contentDescription = null,
                        Modifier.size(26.dp)
                    )
                },
                buttonOneBgColor = Color.Black,
                buttonOneTextSize = 28.sp,
                buttonTwo = "Checkout",
                buttonOneOnClick = { /*TODO*/ },
                buttonTwoOnClick = {}
            )
        }
    }
}

@Composable
fun Footer(item: CartItem) {
    FooterCaption(caption = buildAnnotatedString { append("Enter your prescription during checkout") })
    FooterCaption(caption = buildAnnotatedString { append("Free shipping and returns") })
    FooterCaption(caption = buildAnnotatedString {
        append("You can make ")
        withStyle(
            style = SpanStyle(
                color = MaterialTheme.colors.primaryVariant,
                fontWeight = FontWeight.SemiBold
            )
        ) {
            append("4 interest-free payments ")
        }
        append("of $${ceil(item.style.price / 4.0).toInt()} with Affirm at checkout")
    })
    Divider(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 14.dp)
            .padding(horizontal = 30.dp)
            .clip(RoundedCornerShape(4.dp)),
        thickness = 1.dp,
        color = MaterialTheme.colors.onBackground
    )
}

@Composable
fun FooterCaption(caption: AnnotatedString) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 30.dp)
            .padding(vertical = 8.dp)
    ) {
        androidx.compose.material.Icon(
            Icons.Filled.Check,
            contentDescription = null,
            tint = Color.Gray,
            modifier = Modifier.padding(end = 14.dp)
        )
        Text(caption, fontSize = 18.sp, color = Color.Gray)
    }
}


@Composable
fun CartItem(item: CartItem) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 30.dp, vertical = 14.dp)
            .clip(MaterialTheme.shapes.medium)
            .background(Color.White)
    ) {
        CloseButton(tint = Color.Gray, modifier = Modifier.align(Alignment.End)) {

        }
        Image(
            painter = painterResource(id = R.drawable.sahana),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillWidth
        )
        item.style.brand?.let {
            Text(
                it,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.h5,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp)
            )
        }
        Text(
            item.style.name,
            fontStyle = FontStyle.Italic,
            style = MaterialTheme.typography.overline,
            fontSize = 16.sp,
            fontFamily = FontFamily.Serif,
            modifier = Modifier
                .fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        ItemDescriptionPrice("Frame width", item.frameWidth, -1)
        ItemDescriptionPrice("Prescription type", item.prescriptionType, 145)
        ItemDescriptionPrice("Lens type", item.lensType, 0)
        ItemDescriptionPrice("Lens material", item.lensMaterial, 0)
        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp)
                .clip(RoundedCornerShape(4.dp)),
            thickness = 1.dp,
            color = MaterialTheme.colors.onBackground
        )
        EditSelections(item.style.price.toInt())

    }
}

@Composable
fun EditSelections(price: Int) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
    ) {
        Text(
            "Edit selections",
            style = MaterialTheme.typography.h6,
            color = MaterialTheme.colors.primaryVariant
        )
        Row(verticalAlignment = Alignment.CenterVertically) {
            androidx.compose.material.Icon(
                Icons.Filled.KeyboardArrowUp,
                contentDescription = null,
                tint = Color.Black
            )
            Text("$$price", style = MaterialTheme.typography.h6)
        }
    }
}

@Composable
fun ItemDescriptionPrice(title: String, value: String, price: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp, horizontal = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(title, color = Color.Gray)
            Text(value, fontSize = 18.sp, style = MaterialTheme.typography.button)
        }
        if (price == 0) {
            Text("Free")
        } else if (price > 0) {
            Text("$$price")
        }

    }
}