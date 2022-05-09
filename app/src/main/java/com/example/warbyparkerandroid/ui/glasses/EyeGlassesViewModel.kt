package com.example.warbyparkerandroid.ui.glasses

import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.warbyparkerandroid.data.datasource.AllGlasses
import com.example.warbyparkerandroid.data.model.GlassStyle
import com.example.warbyparkerandroid.data.model.Glasses
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class EyeGlassesViewModel : ViewModel() {

    private var _eyeGlasses = MutableLiveData<SnapshotStateList<Glasses>>()
    val eyeGlasses get() = _eyeGlasses

    init {
        toggleGlassesVisibility()
    }

    private fun toggleGlassesVisibility() {
        _eyeGlasses.value = AllGlasses.toMutableStateList()
        viewModelScope.launch {
            delay(30)
            _eyeGlasses.value?.forEach {
                it.visible = MutableTransitionState(false).apply { targetState = true }
            }
        }
    }

    fun update(glassStyle: GlassStyle) {
        _eyeGlasses.value?.forEach { glass ->
            val style = glass.styles.find { style -> style.name == glassStyle.name }
            if (style != null) {
                style.isFavorite = glassStyle.isFavorite
            }
        }
    }

    override fun onCleared() {
        _eyeGlasses.value?.forEach {
            it.visible = MutableTransitionState(false)
        }
        super.onCleared()
    }
}