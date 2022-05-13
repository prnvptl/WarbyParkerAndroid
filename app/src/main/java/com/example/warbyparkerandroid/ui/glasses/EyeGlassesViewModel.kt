package com.example.warbyparkerandroid.ui.glasses

import android.util.Log
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.warbyparkerandroid.data.datasource.AllGlasses
import com.example.warbyparkerandroid.data.model.GlassStyle
import com.example.warbyparkerandroid.data.model.Glasses
import kotlinx.coroutines.launch

class EyeGlassesViewModel : ViewModel() {

    private var _eyeGlasses = MutableLiveData<SnapshotStateList<Glasses>>()
    val eyeGlasses get() = _eyeGlasses

    private var _favoritesCount = MutableLiveData<Int>(0)
    val favoritesCount get() = _favoritesCount

    init {
        toggleGlassesVisibility()
    }

    private fun toggleGlassesVisibility() {
        _eyeGlasses.value = AllGlasses.toMutableStateList()
        viewModelScope.launch {
//            delay(30)
            _eyeGlasses.value?.forEach {
                it.visible = true
            }
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
            _favoritesCount.postValue(getFavoritesCount())
        }
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

    override fun onCleared() {
        _eyeGlasses.value?.forEach {
            it.visible = false
        }
        super.onCleared()
    }
}