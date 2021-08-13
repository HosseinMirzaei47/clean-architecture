package com.app.core

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(val savedState: SavedStateHandle) : ViewModel() {
    init {
        savedState.get<Unit>("")
    }
}