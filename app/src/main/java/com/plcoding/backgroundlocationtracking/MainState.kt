package com.plcoding.backgroundlocationtracking

import android.location.Location
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData

class MainState {
    data class State(
        var locationList: MutableLiveData<MutableList<Location>> = MutableLiveData(mutableListOf())
    )
}