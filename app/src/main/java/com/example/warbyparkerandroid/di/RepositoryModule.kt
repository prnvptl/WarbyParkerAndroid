package com.example.warbyparkerandroid.di

import com.example.warbyparkerandroid.data.repository.GlassesRepository
import com.example.warbyparkerandroid.data.repository.GlassesRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun providesGlassesRepository() : GlassesRepositoryImpl {
        return GlassesRepositoryImpl()
    }
}