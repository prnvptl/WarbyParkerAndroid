package com.example.warbyparkerandroid.ui.account

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material3.*
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.warbyparkerandroid.R
import com.example.warbyparkerandroid.ui.common.CloseButton
import com.example.warbyparkerandroid.ui.glasses.CollapsableHandle
import com.example.warbyparkerandroid.ui.login.SignIn
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

data class AccountItem(val icon: Int, val title: String, val onClick: () -> Unit)

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun Account(
    hideBottomNav: () -> Unit,
    onPDMeasurement: () -> Unit,
    showBottomNav: () -> Unit,
) {
    val modalState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmStateChange = {
            if (it == ModalBottomSheetValue.Hidden) {
                showBottomNav()
            }
            it != ModalBottomSheetValue.HalfExpanded
        })
    val scope = rememberCoroutineScope()
    val accountItems = listOf<AccountItem>(AccountItem(
        icon = R.drawable.pd_icon, title = "Measure pupillary distance\n(PD)", onPDMeasurement
    ),
        AccountItem(icon = R.drawable.prescription_icon, title = "Get a prescription", {}),
        AccountItem(icon = R.drawable.location_icon, title = "Locations", {}),
        AccountItem(icon = R.drawable.faq_icon, title = "FAQ", {}),
        AccountItem(icon = R.drawable.notification_icon, title = "Notifications", {}),
        AccountItem(icon = R.drawable.pencil_icon, title = "Write a review", {})

    )
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
        Scaffold(topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = "Account", style = MaterialTheme.typography.h5) },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.White
                ),
            )
        }) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
            ) {
                item {
                    AccountHeader() {
                        scope.launch {
                            hideBottomNav()
                            delay(300)
                            modalState.animateTo(ModalBottomSheetValue.Expanded)
                        }
                    }
                }
                items(accountItems) {
                    AccountListItem(icon = it.icon, title = it.title) {
                        it.onClick()
                    }
                }
                item {
                    Footer()
                }
            }
        }
    }
}

@Composable
fun Footer() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Need help?",
            fontSize = 26.sp,
            fontWeight = FontWeight.SemiBold,
            fontFamily = FontFamily.Serif,
            modifier = Modifier.padding(horizontal = 12.dp)
        )
        Text(
            text = "We're available by phone (855.914.5678) and chat (646.233.2186) every day, 9 a.m.-10 p.m. ET.",
            textAlign = TextAlign.Center,
            fontSize = 20.sp,
            fontWeight = FontWeight.Light,
        )
        Row {
            Button(
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .height(32.dp)
                    .width(135.dp)
                    .padding(start = 10.dp, end = 5.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primaryVariant),
                shape = MaterialTheme.shapes.large
            ) {
                Text("Email", color = Color.White, fontSize = 16.sp)

            }
            Button(
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .height(32.dp)
                    .width(135.dp)
                    .padding(start = 10.dp, end = 5.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primaryVariant),
                shape = MaterialTheme.shapes.large
            ) {
                Text("Phone", color = Color.White, fontSize = 16.sp)

            }
            Button(
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .height(32.dp)
                    .width(135.dp)
                    .padding(start = 10.dp, end = 5.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primaryVariant),
                shape = MaterialTheme.shapes.large
            ) {
                Text("Chat", color = Color.White, fontSize = 16.sp)
            }
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "\uD83C\uDDFA\uD83C\uDDF8 Change location?",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = "Privacy Policy", style = androidx.compose.ui.text.TextStyle(
                    textDecoration = TextDecoration.Underline, fontSize = 16.sp
                )
            )
            Text(
                text = "Notice of Privacy Practices", style = androidx.compose.ui.text.TextStyle(
                    textDecoration = TextDecoration.Underline, fontSize = 16.sp
                )
            )
            Text(
                text = "Terms of Use", style = androidx.compose.ui.text.TextStyle(
                    textDecoration = TextDecoration.Underline, fontSize = 16.sp
                )
            )
            Text(
                text = "Accessibility", style = androidx.compose.ui.text.TextStyle(
                    textDecoration = TextDecoration.Underline, fontSize = 16.sp
                )
            )
            Text(text = " Version 25.0.0.0/1234", fontWeight = FontWeight.ExtraLight)
        }
    }
}

@Composable
fun AccountListItem(icon: Int, title: String, onClick: () -> Unit) {
    Column(modifier = Modifier.background(Color.White)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 22.dp)
                .clickable { onClick() },
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(horizontal = 12.dp)
            ) {
                Image(
                    painter = painterResource(id = icon),
                    contentDescription = null,
                    contentScale = ContentScale.FillHeight,
                    modifier = Modifier.size(50.dp)
                )

                Text(
                    title,
                    textAlign = TextAlign.Left,
                    fontSize = 24.sp,
                    fontStyle = FontStyle.Normal,
                    fontWeight = FontWeight.Normal,
                    fontFamily = FontFamily.Serif,
                    modifier = Modifier.padding(horizontal = 12.dp)
                )
            }
            Image(
                painter = painterResource(id = R.drawable.arrow_right_icon),
                contentDescription = null,
                modifier = Modifier.size(40.dp)
            )
        }
        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(4.dp)),
            thickness = 1.dp,
            color = MaterialTheme.colors.onBackground
        )
    }
}

@Composable
fun AccountHeader(onSignIn: () -> Unit) {
    Box(modifier = Modifier.fillMaxWidth()) {
        Image(
            painter = painterResource(id = R.drawable.accounts_header),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillWidth
        )
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(horizontal = 50.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Sign in or create an account to shop, view orders, and more.",
                modifier = Modifier.padding(horizontal = 30.dp),
                textAlign = TextAlign.Center,
                fontSize = 24.sp,
                fontStyle = FontStyle.Normal,
                fontWeight = FontWeight.Normal,
                fontFamily = FontFamily.Serif
            )
            Row(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 36.dp)
            ) {
                Button(
                    onClick = { onSignIn() },
                    modifier = Modifier
                        .height(40.dp)
                        .width(175.dp)
                        .padding(start = 10.dp, end = 5.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primaryVariant),
                    shape = MaterialTheme.shapes.large
                ) {
                    Text("Create Account", color = Color.White, fontSize = 18.sp)
                }
                Button(
                    onClick = { onSignIn() },
                    modifier = Modifier
                        .height(40.dp)
                        .width(175.dp)
                        .padding(start = 10.dp, end = 5.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary),
                    shape = MaterialTheme.shapes.large
                ) {
                    Text("Sign in", color = Color.White, fontSize = 18.sp)
                }
            }
        }
    }
}