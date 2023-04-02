package com.dschu.drivermatch.ui.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.dschu.drivermatch.viewmodel.DriverMatchVM

@OptIn(ExperimentalMaterial3Api::class)
@ExperimentalFoundationApi
@Composable
fun DriverListScreen(viewModel: DriverMatchVM, onDriverClicked: (String) -> Unit) {
    val drivers by viewModel.driverList.observeAsState(null)
    drivers?.let { driverList ->
        Scaffold(modifier = Modifier.fillMaxSize()) {
            Surface(modifier = Modifier.fillMaxSize().padding(it)) {
                LazyVerticalGrid(columns = GridCells.Fixed(1),
                    contentPadding = PaddingValues(4.dp)
                ) {
                    items(driverList.size) { index ->
                        DriverCard(driver = driverList[index], onClicked = { onDriverClicked(driverList[index]) })
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DriverCard(driver: String, onClicked: () -> Unit) {
    Card(
        modifier = Modifier
            .wrapContentWidth()
            .height(IntrinsicSize.Max)
            .padding(top = 4.dp, bottom = 4.dp, start = 8.dp, end = 8.dp)
            .clickable(onClick = onClicked)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = driver, modifier = Modifier.padding(8.dp))
        }
    }
}