package com.example.warbyparkerandroid.ui.contacts

import android.annotation.SuppressLint
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.warbyparkerandroid.R

@OptIn(ExperimentalFoundationApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun Contacts(
    viewModel: ContactsViewModel = viewModel(),
    onBack: () -> Unit,
) {
    Text("Contacts")
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(onClick = { onBack() }) {
                        Icon(
                            Icons.Filled.ArrowBack,
                            contentDescription = null,
                            tint = MaterialTheme.colors.primaryVariant
                        )
                    }
                }
            )
        }
    ) {
        var brandSearch by remember { mutableStateOf("") }
        val contacts = viewModel.contacts.observeAsState(initial = emptyList()).value
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 36.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            item {
                Text("Contacts", style = MaterialTheme.typography.h5)
            }
            item {
                Image(
                    painter = painterResource(id = R.drawable.contacts_header),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(MaterialTheme.shapes.medium),
                    contentScale = ContentScale.FillWidth
                )
            }

            item {
                Surface(
                    shape = RoundedCornerShape(50),
                    elevation = 3.dp,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    TextField(
                        value = brandSearch,
                        onValueChange = {
                            brandSearch = it
                            viewModel.search(brandSearch)
                        },
                        placeholder = {
                            Text(
                                "Find my brand",
                                color = Color.Black,
                                fontSize = 20.sp
                            )
                        },
                        maxLines = 1,
                        trailingIcon = {
                            IconButton(onClick = { }) {
                                Icon(
                                    painter = painterResource(id = com.example.warbyparkerandroid.R.drawable.ic_baseline_qr_code_scanner_24),
                                    contentDescription = null,
                                    tint = MaterialTheme.colors.primaryVariant
                                )
                            }
                        },
                        leadingIcon = {
                            IconButton(onClick = { }) {
                                Icon(
                                    Icons.Filled.Search,
                                    contentDescription = null,
                                    tint = MaterialTheme.colors.primaryVariant
                                )
                            }
                        },
                        colors = TextFieldDefaults.textFieldColors(focusedIndicatorColor = Color.Transparent, backgroundColor = Color.White),
                    )
                }
            }
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    var acuveFilter by remember {
                        mutableStateOf(false)
                    }
                    Image(
                        painter = painterResource(id = R.drawable.acuve),
                        contentDescription = null,
                        modifier = Modifier
                            .height(50.dp)
                            .width(100.dp)
                            .border(
                                BorderStroke(
                                    1.dp,
                                    if (acuveFilter) MaterialTheme.colors.primaryVariant else Color.LightGray
                                ), shape = MaterialTheme.shapes.medium
                            )
                            .clickable {
                                brandSearch = ""
                                acuveFilter = !acuveFilter
                                if (acuveFilter) {
                                    viewModel.search("acuvue")
                                } else {
                                    viewModel.search("")
                                }
                            }
                    )
                    var dailiesFilter by remember {
                        mutableStateOf(false)
                    }
                    Image(
                        painter = painterResource(id = R.drawable.dailies),
                        contentDescription = null,
                        modifier = Modifier
                            .height(50.dp)
                            .width(100.dp)
                            .border(
                                BorderStroke(
                                    1.dp,
                                    if (dailiesFilter) MaterialTheme.colors.primaryVariant else Color.LightGray
                                ), shape = MaterialTheme.shapes.medium
                            )
                            .clickable {
                                brandSearch = ""
                                dailiesFilter = !dailiesFilter
                                if (dailiesFilter) {
                                    viewModel.search("dailies")
                                } else {
                                    viewModel.search("")
                                }
                            }
                    )
                    var biofinityFilter by remember {
                        mutableStateOf(false)
                    }
                    Image(
                        painter = painterResource(id = R.drawable.bioinfinity),
                        contentDescription = null,
                        modifier = Modifier
                            .height(50.dp)
                            .width(100.dp)
                            .border(
                                BorderStroke(
                                    1.dp,
                                    if (biofinityFilter) MaterialTheme.colors.primaryVariant else Color.LightGray
                                ), shape = MaterialTheme.shapes.medium
                            )
                            .clickable {
                                brandSearch = ""
                                biofinityFilter = !biofinityFilter
                                if (biofinityFilter) {
                                    viewModel.search("bio")
                                } else {
                                    viewModel.search("")
                                }
                            }
                            .align(Alignment.CenterVertically)
                    )
                }
            }

            items(contacts, key = { it.name }) {
                AnimatedVisibility(
                    visible = it.visible,
                    enter = slideInVertically { 300 } + fadeIn(tween(400)),
                    exit = slideOutVertically { 300 } + fadeOut(tween(400)),
                    modifier = Modifier.animateItemPlacement()
                ) {
                    Column(Modifier.animateItemPlacement().padding(vertical = 11.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                        Image(
                            painter = painterResource(it.image),
                            contentDescription = null,
                            modifier = Modifier.fillMaxWidth(),
                            contentScale = ContentScale.FillWidth
                        )
                        Text(it.name,                     textAlign = TextAlign.Center,
                            fontSize = 32.sp,
                            fontStyle = FontStyle.Normal,
                            fontWeight = FontWeight.Normal,
                            fontFamily = FontFamily.Serif,)
                        Text(it.pack,
                            textAlign = TextAlign.Center,
                            fontSize = 20.sp,
                            fontStyle = FontStyle.Italic,
                            fontWeight = FontWeight.Normal,
                            fontFamily = FontFamily.Serif)
                    }
                }
            }
        }
    }
}