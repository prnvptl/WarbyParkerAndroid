package com.example.warbyparkerandroid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.LocalElevationOverlay
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.warbyparkerandroid.ui.RootScreen
import com.example.warbyparkerandroid.ui.bottomnavigation.WPBottomNavBar
import com.example.warbyparkerandroid.ui.theme.WarbyParkerAndroidTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RootScreen()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    WarbyParkerAndroidTheme {
        CompositionLocalProvider(LocalElevationOverlay provides null) {
            RootScreen()
        }
    }
}