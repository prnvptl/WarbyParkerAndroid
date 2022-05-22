package com.example.warbyparkerandroid.di

import android.content.Context
import com.example.warbyparkerandroid.data.repository.GlassesRepository
import com.example.warbyparkerandroid.data.repository.GlassesRepositoryImpl

interface AppContainer {
    val glassesRepository: GlassesRepository
}

class AppContainerImpl(private val applicationContext: Context): AppContainer {

    override val glassesRepository: GlassesRepository by lazy {
        GlassesRepositoryImpl()
    }
}