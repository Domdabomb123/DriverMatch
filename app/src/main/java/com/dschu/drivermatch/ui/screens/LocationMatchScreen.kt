package com.dschu.drivermatch.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.dschu.drivermatch.viewmodel.DriverMatchVM

@Composable
fun LocationMatchScreen(
    viewModel: DriverMatchVM,
    driver: String
) {
    viewModel.matchDriver(driver)
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = driver, modifier = Modifier.padding(10.dp))

        val location by viewModel.locationMatch.observeAsState(null)
        location?.let { location ->
            Text(text = location, modifier = Modifier.padding(10.dp))
        }
    }
}