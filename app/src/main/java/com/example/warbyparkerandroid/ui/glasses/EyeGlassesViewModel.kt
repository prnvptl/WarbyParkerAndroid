package com.example.warbyparkerandroid.ui.glasses

import android.util.Log
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.warbyparkerandroid.data.datasource.AllGlasses
import com.example.warbyparkerandroid.data.datasource.Result
import com.example.warbyparkerandroid.data.model.GlassStyle
import com.example.warbyparkerandroid.data.model.Glasses
import com.example.warbyparkerandroid.data.repository.GlassesRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

/**
 * UI state for the Eyeglasses route.
 */
sealed interface GlassesUiState {
    val isLoading: Boolean
    val isError: Boolean
    val eyeGlasses: List<Glasses>
    val favoriteCount: Int

    data class NoGlasses(
        override val isLoading: Boolean,
        override val isError: Boolean,
        override val eyeGlasses: List<Glasses>,
        override val favoriteCount: Int,
    ) : GlassesUiState

    data class HasGlasses(
        override val isLoading: Boolean,
        override val isError: Boolean,
        override val eyeGlasses: List<Glasses>,
        override val favoriteCount: Int,
    ) : GlassesUiState
}

private data class GlassesViewModelState(
    val isLoading: Boolean = false,
    val eyeGlasses: List<Glasses> = listOf(),
    val isError: Boolean = false,
    val favoriteCount: Int = 0
) {
    fun toUiState(): GlassesUiState =
        if (eyeGlasses.isEmpty()) {
            GlassesUiState.NoGlasses(
                isLoading = isLoading,
                isError = isError,
                eyeGlasses = eyeGlasses,
                favoriteCount = favoriteCount
            )
        } else {
            GlassesUiState.HasGlasses(
                isLoading = isLoading,
                isError = isError,
                eyeGlasses = eyeGlasses,
                favoriteCount = favoriteCount
            )
        }
}

class EyeGlassesViewModel(
    private val glassesReposiory: GlassesRepository
) : ViewModel() {
    private val viewModelState = MutableStateFlow(GlassesViewModelState(isLoading = true))

    val uiState = viewModelState.map { it.toUiState() }.stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        viewModelState.value.toUiState()
    )

    init {
        viewModelState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            val result = glassesReposiory.getGlasses()
            viewModelState.update {
                when (result) {
                    is Result.Success<*> -> {
                        val glasses = result.data as List<Glasses>
                        Log.i("viewModelStateSUCCESS: ", glasses.size.toString())
                        it.copy(eyeGlasses = glasses, isLoading = false)
                    }
                    is Result.Error -> {
                        it.copy(isLoading = false, isError = true)
                    }
                }
            }
        }

        viewModelScope.launch {
            glassesReposiory.observeEyeglasses().collect { favorites ->
                viewModelState.update { it.copy(eyeGlasses = favorites) }
            }
            glassesReposiory.observeFavorites().collect { fav ->
                viewModelState.update { it.copy(favoriteCount = fav) }
            }
        }
    }

    fun update(glassStyle: GlassStyle) {
        viewModelScope.launch {
            glassesReposiory.updateGlass(glassStyle)
            viewModelState.update {
                val newFavCount = glassesReposiory.getFavoirteCount()
                it.copy(favoriteCount = newFavCount)
            }
        }
    }

    fun getFavoritesCount(): Int {
        val favPredicate: (GlassStyle) -> Boolean = { it.isFavorite }
        var favCount = 0
        uiState.value.eyeGlasses.forEach { glass ->
            favCount += glass.styles.count(favPredicate)
        }
        return favCount
    }

    fun search(term: String) {
        viewModelScope.launch {
            glassesReposiory.searchGlass(term)
        }
    }

    /**
     * Factory for HomeViewModel that takes PostsRepository as a dependency
     */
    companion object {
        fun provideFactory(
            glassesRepository: GlassesRepository,
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return EyeGlassesViewModel(glassesRepository) as T
            }
        }
    }
}