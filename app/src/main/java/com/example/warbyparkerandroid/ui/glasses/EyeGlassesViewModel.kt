package com.example.warbyparkerandroid.ui.glasses

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.warbyparkerandroid.data.datasource.AllGlasses
import com.example.warbyparkerandroid.data.model.GlassStyle

class EyeGlassesViewModel : ViewModel() {
    private var _eyeGlasses = MutableLiveData(AllGlasses)
    val eyeGlasses get() = _eyeGlasses

    fun update(glassStyle: GlassStyle) {
        _eyeGlasses.value?.forEach { glass ->
            val style = glass.styles.find { style -> style.name == glassStyle.name }
            if (style != null) {
                style.isFavorite = glassStyle.isFavorite
            }
        }
    }
}