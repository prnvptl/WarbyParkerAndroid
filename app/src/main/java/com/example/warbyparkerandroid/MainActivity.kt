package com.example.warbyparkerandroid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.LocalElevationOverlay
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
import com.example.warbyparkerandroid.ui.RootScreen
import com.example.warbyparkerandroid.ui.glasses.EyeGlassesViewModel
import com.example.warbyparkerandroid.ui.theme.WarbyParkerAndroidTheme


class MainActivity : ComponentActivity() {
    private val mEyeGlassesViewModel: EyeGlassesViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RootScreen()
        }
    }
}