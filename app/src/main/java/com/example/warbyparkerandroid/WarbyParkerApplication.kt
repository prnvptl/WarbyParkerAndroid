package com.example.warbyparkerandroid

import android.app.Application
import com.example.warbyparkerandroid.di.AppContainer
import com.example.warbyparkerandroid.di.AppContainerImpl
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class WarbyParkerApplication : Application() {

    // AppContainer instance used by the rest of classes to obtain dependencies
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppContainerImpl(this)
    }
}