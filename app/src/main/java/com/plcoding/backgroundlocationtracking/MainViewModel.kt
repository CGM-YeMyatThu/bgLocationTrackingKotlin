package com.plcoding.backgroundlocationtracking

import android.location.Location
import android.util.Log
import android.view.View
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {
    var state by mutableStateOf(MainState.State())

    init {
        MainContract.locationList.observeForever { location: MutableList<Location> ->
            state = state.copy(locationList = MutableLiveData(location))
            Log.d("aabb", state.locationList.toString())
        }
    }

    override fun onCleared() {
        MainContract.locationList.removeObserver { location: MutableList<Location> ->
            Log.d("aa", location.size.toString())
        }
    }
}

