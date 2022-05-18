package com.example.warbyparkerandroid.ui.cart

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
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
import com.example.warbyparkerandroid.R
import com.example.warbyparkerandroid.ui.common.CloseButton
import com.example.warbyparkerandroid.ui.common.FloatingActionButtons

@Composable
fun Cart(onBack: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFf8f7f9)),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            item {
                CloseButton(modifier = Modifier.padding(top = 12.dp, start = 12.dp)) {
                    onBack()
                }
            }
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
            item {
                CartItem()
            }

            item {
                Footer()
            }

            item {
                Spacer(modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp))
            }
        }
        FloatingActionButtons(
            modifier = Modifier.align(Alignment.BottomCenter).padding(bottom = 20.dp),
            buttonOne = "Pay",
            ButtonOneIcon = {
                Image(painter = painterResource(id = R.drawable.g), contentDescription = null, Modifier.size(26.dp))
            },
            buttonOneBgColor = Color.Black,
            buttonOneTextSize = 28.sp,
            buttonTwo = "Checkout",
            buttonOneOnClick = { /*TODO*/ },
            buttonTwoOnClick = {}
        )
    }
}

@Composable
fun Footer() {
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
        append("of $37 with Affirm at checkout")
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

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
            .padding(horizontal = 30.dp)
            .padding(top = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text("Shipping", fontSize = 18.sp, style = MaterialTheme.typography.button)
        Text("Free", color = Color.Gray)
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 30.dp)
            .padding(vertical = 6.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text("Subtotal", fontSize = 18.sp, style = MaterialTheme.typography.button)
        Text("$145", color = Color.Gray)
    }
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
fun CartItem() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 30.dp)
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
        Text(
            "Sahana",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.h5,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp)
        )
        Text(
            "Brushed Ink",
            fontStyle = FontStyle.Italic,
            style = MaterialTheme.typography.overline,
            fontSize = 16.sp,
            fontFamily = FontFamily.Serif,
            modifier = Modifier
                .fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        ItemDescriptionPrice("Frame width", "Medium", -1)
        ItemDescriptionPrice("Prescription type", "Single-vision", 145)
        ItemDescriptionPrice("Lens type", "Classic", 0)
        ItemDescriptionPrice("Lens material", "Polycarbonate", 0)
        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp)
                .clip(RoundedCornerShape(4.dp)),
            thickness = 1.dp,
            color = MaterialTheme.colors.onBackground
        )
        EditSelections(145)

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