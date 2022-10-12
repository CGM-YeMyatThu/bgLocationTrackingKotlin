package com.plcoding.backgroundlocationtracking

import android.Manifest
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.runtime.livedata.observeAsState
import com.plcoding.backgroundlocationtracking.ui.theme.BackgroundLocationTrackingTheme
import java.text.SimpleDateFormat
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
            ),
            0
        )
        setContent {
            BackgroundLocationTrackingTheme {
                Scaffold(
                    topBar = { TopBarComp() },
                    bottomBar = { BottomBar() }
                ) {
                    val viewModel: MainViewModel = hiltViewModel()
                    var locList = viewModel.state.locationList.observeAsState()
                    LazyColumn(modifier = Modifier.padding(it)) {
                        itemsIndexed(locList.value ?: mutableListOf()) { index, location ->
                            Card(
                                modifier = Modifier
                                    .padding(16.dp)
                                    .fillMaxWidth(),
                                elevation = 3.dp
                            ) {
                                Row() {
                                    Text(
                                        modifier = Modifier.padding(
                                            start = 16.dp,
                                            top = 16.dp,
                                            end = 4.dp
                                        ),
                                        text = "${index+1}."
                                    )
                                    Column(
                                        modifier = Modifier
                                            .padding(16.dp)
                                            .weight(1f)
                                    ) {
                                        val time = SimpleDateFormat("hh:mm:ss").format(location.time)
                                        Text( text = "Latitude : ${location.latitude}")
                                        Text(text = "Longitude : ${location.longitude}")
                                        Text(text = "Time : $time")
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun TopBarComp() {

    }

    @Composable
    fun BottomBar() {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(onClick = {
                Intent(applicationContext, LocationService::class.java).apply {
                    action = LocationService.ACTION_START
                    startService(this)
                }
            }) {
                Text(text = "Start")
            }
            Spacer(modifier = Modifier.width(16.dp))
            Button(onClick = {
                Intent(applicationContext, LocationService::class.java).apply {
                    action = LocationService.ACTION_STOP
                    startService(this)
                }
            }) {
                Text(text = "Stop")
            }
        }
    }
}