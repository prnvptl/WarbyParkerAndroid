package com.example.warbyparkerandroid

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class WarbyParkerApplication : Application() {
    override fun onCreate() {
        super.onCreate() }
}