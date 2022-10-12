package com.plcoding.backgroundlocationtracking

import android.location.Location
import androidx.lifecycle.MutableLiveData

class MainContract {
    companion object {
        val locationList: MutableLiveData<MutableList<Location>> by lazy { MutableLiveData<MutableList<Location>>() }

    }
}