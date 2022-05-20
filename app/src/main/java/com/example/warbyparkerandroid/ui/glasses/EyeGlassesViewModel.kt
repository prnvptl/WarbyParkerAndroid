package com.example.warbyparkerandroid.ui.glasses

import android.os.Parcelable
import android.util.Log
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.warbyparkerandroid.data.datasource.AllContacts
import com.example.warbyparkerandroid.data.datasource.AllGlasses
import com.example.warbyparkerandroid.data.model.GlassStyle
import com.example.warbyparkerandroid.data.model.Glasses
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.parcelize.Parcelize

@Parcelize
class EyeGlassesViewModel : ViewModel(), Parcelable {

    private var _eyeGlasses = MutableLiveData<SnapshotStateList<Glasses>>()
    val eyeGlasses get() = _eyeGlasses

    private var _favoritesCount = MutableLiveData<Int>(0)
    val favoritesCount get() = _favoritesCount

    var _uiState = MutableLiveData<EyeGlassesUiState>(null)
        private set

    init {
        toggleGlassesVisibility()
    }

    private fun toggleGlassesVisibility() {
        _eyeGlasses.value = AllGlasses.toMutableStateList()
        viewModelScope.launch {
            _uiState.value = EyeGlassesUiState.Loading
            delay(3000)
            _eyeGlasses.value?.forEach {
                it.visible = MutableTransitionState(false).apply { targetState = true }
            }
            _uiState.value = EyeGlassesUiState.Success
        }
    }

    fun update(glassStyle: GlassStyle) {
        viewModelScope.launch {
            _eyeGlasses.value?.forEach { glass ->
                val style = glass.styles.find { style -> style.name == glassStyle.name }
                if (style != null) {
                    style.isFavorite = glassStyle.isFavorite
                }
            }
        }
        _favoritesCount.value = getFavoritesCount()
    }

    fun updateCount() {
        _favoritesCount.value = getFavoritesCount()
    }

    private fun getFavoritesCount(): Int {
        val favPredicate: (GlassStyle) -> Boolean = { it.isFavorite }
        var favCount = 0
        _eyeGlasses.value?.forEach { glass ->
            favCount += glass.styles.count(favPredicate)
        }
        Log.i("favStateCount ", favCount.toString())
        return favCount
    }

    fun search(term: String) {
        if(term.isNullOrBlank()) {
            _eyeGlasses.value = AllGlasses.toMutableStateList()
        } else {
            val results =  AllGlasses.filter { it.brand.contains(term, ignoreCase = true) }.toMutableStateList()
            _eyeGlasses.value = results
        }
    }

    override fun onCleared() {
        _eyeGlasses.value?.forEach {
            it.visible = MutableTransitionState(false)
        }
        _uiState.value = EyeGlassesUiState.Loading
        super.onCleared()
    }
}

sealed class EyeGlassesUiState {
    object Loading : EyeGlassesUiState()
    object Failure : EyeGlassesUiState()
    object Success : EyeGlassesUiState()
}