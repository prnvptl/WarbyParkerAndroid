package com.example.warbyparkerandroid.data.repository

import android.util.Log
import androidx.compose.runtime.toMutableStateList
import com.example.warbyparkerandroid.data.datasource.AllGlasses
import com.example.warbyparkerandroid.data.datasource.Result
import com.example.warbyparkerandroid.data.model.GlassStyle
import com.example.warbyparkerandroid.data.model.Glasses
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.withContext

// Local & network datasources parameters
class GlassesRepositoryImpl(
    // val localDataStore: LocalGlassesDatasource
    // val networkDataSource: NetworkGlassessDatasource
) : GlassesRepository {

    // for now, store these in memory
    private val eyeGlasses = MutableStateFlow<List<Glasses>>(listOf())
    private val favoriteCount = MutableStateFlow<Int>(0)

    override suspend fun getGlasses(): Result<List<Glasses>> {
        return withContext(Dispatchers.IO) {
            try {
                delay(5000)
                eyeGlasses.value = AllGlasses
                Result.Success(eyeGlasses.value)
            } catch (ex: Exception) {
                Result.Error(ex)
            }

        }
    }

    override suspend fun updateGlass(glassStyle: GlassStyle) {
        return withContext(Dispatchers.IO) {
            eyeGlasses.value.forEach { glass ->
                val style = glass.styles.find { style -> style.name == glassStyle.name }
                if (style != null) {
                    style.isFavorite = glassStyle.isFavorite
                }
            }
        }
    }

    override suspend fun searchGlass(term: String): Result<List<Glasses>> {
        return withContext(Dispatchers.IO) {
            try {
                if (term.isNullOrBlank()) {
                    eyeGlasses.value = AllGlasses.toMutableStateList()
                } else {
                    val results = AllGlasses.filter { it.brand.contains(term, ignoreCase = true) }
                        .toMutableStateList()
                    eyeGlasses.value = results
                }
                Result.Success(eyeGlasses.value)
            } catch (ex: Exception) {
                Result.Error(ex)
            }

        }

    }

    override fun observeEyeglasses(): Flow<List<Glasses>> = eyeGlasses
    override fun observeFavorites(): Flow<Int> = favoriteCount

    override suspend fun getFavoirteCount(): Int {
        return withContext(Dispatchers.IO) {
            val favPredicate: (GlassStyle) -> Boolean = { it.isFavorite }
            var favCount = 0
            eyeGlasses.value.forEach { glass ->
                favCount += glass.styles.count(favPredicate)
            }
            favoriteCount.value = favCount
            favCount
        }
    }
}