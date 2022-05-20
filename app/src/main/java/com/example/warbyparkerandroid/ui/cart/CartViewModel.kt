package com.example.warbyparkerandroid.ui.cart

import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.warbyparkerandroid.data.datasource.CartItems
import com.example.warbyparkerandroid.data.model.CartItem
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class CartViewModel : ViewModel() {

    private var _cartItems = MutableLiveData<SnapshotStateList<CartItem>>()
    val cartItems get() = _cartItems

    init {
        _cartItems.value = CartItems.toMutableStateList()
        viewModelScope.launch {
            delay(300)
            _cartItems.value?.forEach {
                it.visible = MutableTransitionState<Boolean>(false).apply { targetState = true }
            }
        }
    }

    override fun onCleared() {
        _cartItems.value?.forEach {
            it.visible = MutableTransitionState(false)
        }
        super.onCleared()
    }
}