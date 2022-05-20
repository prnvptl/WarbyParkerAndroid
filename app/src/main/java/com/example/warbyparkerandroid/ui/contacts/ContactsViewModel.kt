package com.example.warbyparkerandroid.ui.contacts

import android.util.Log
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.warbyparkerandroid.data.datasource.AllContacts
import com.example.warbyparkerandroid.data.model.Contacts
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ContactsViewModel : ViewModel() {
    var contacts = MutableLiveData<SnapshotStateList<Contacts>>()
        private set

    init {
        contacts.value = AllContacts.toMutableStateList()
        viewModelScope.launch {
            delay(300)
            contacts.value?.forEach {
                it.visible = true
            }
        }
    }

    fun search(term: String) {
        if(term.isNullOrBlank()) {
            contacts.value = AllContacts.toMutableStateList()
        } else {
            val results =  AllContacts.filter { it.name.contains(term, ignoreCase = true) }.toMutableStateList()
            Log.i("ContactsViewModel: ", "${results.size}")
            contacts.value = results
        }
    }

    override fun onCleared() {
        contacts.value?.forEach {
            it.visible = false
        }
        super.onCleared()
    }
}