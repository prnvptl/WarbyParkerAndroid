package com.example.warbyparkerandroid.data.repository

import com.example.warbyparkerandroid.data.model.Glasses
import com.example.warbyparkerandroid.data.datasource.Result
import com.example.warbyparkerandroid.data.model.GlassStyle
import kotlinx.coroutines.flow.Flow

interface GlassesRepository {
    suspend fun getGlasses() : Result<List<Glasses>>
    suspend fun updateGlass(style: GlassStyle)
    suspend fun searchGlass(term: String) : Result<List<Glasses>>
    suspend fun getFavoirteCount() : Int
    /**
     * Observe the current eyeglasses
     */
    fun observeEyeglasses(): Flow<List<Glasses>>
    fun observeFavorites(): Flow<Int>
}