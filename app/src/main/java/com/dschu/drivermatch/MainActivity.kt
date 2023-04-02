package com.dschu.drivermatch

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import com.dschu.drivermatch.ui.navigation.DriverList
import com.dschu.drivermatch.ui.navigation.DriverMatchDestinations
import com.dschu.drivermatch.ui.navigation.DriverMatchNavHost
import com.dschu.drivermatch.ui.navigation.driverMatchScreens
import com.dschu.drivermatch.ui.theme.DriverMatchTheme
import com.dschu.drivermatch.util.DriverMatchViewModelFactory
import com.dschu.drivermatch.util.getJsonDataFromAsset
import com.dschu.drivermatch.viewmodel.DriverMatchVM
import com.google.accompanist.navigation.animation.rememberAnimatedNavController

class MainActivity : ComponentActivity() {

    private val driverMatchVM: DriverMatchVM by viewModels{
        DriverMatchViewModelFactory()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getJsonDataFromAsset(applicationContext, "data.json")?.let { jsonString ->
            driverMatchVM.readJsonData(jsonString)
        }

        setContent {
            DriverMatchApp(driverMatchVM)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun DriverMatchApp(driverMatchViewModel: DriverMatchVM) {
    DriverMatchTheme {
        val navController = rememberAnimatedNavController()
        val currentBackStack by navController.currentBackStackEntryAsState()
        val currentDestination = currentBackStack?.destination
        val currentScreen =
            driverMatchScreens.find { currentDestination?.route?.contains(it.route) ?: false }
                ?: DriverList

        Scaffold(
            topBar = {
                DriverMatchAppBar(
                    currentScreen = currentScreen,
                    onBackClicked = { navController.popBackStack() }
                )
            }
        ) { innerPadding ->
            DriverMatchNavHost(
                navController = navController,
                modifier = Modifier.padding(innerPadding),
                viewModel = driverMatchViewModel
            )
        }
    }
}

@Composable
fun DriverMatchAppBar(
    currentScreen: DriverMatchDestinations,
    onBackClicked: () -> Unit
) {
    CenterAlignedTopAppBar(
        title = { Text(text = currentScreen.screenTitle) },
        navigationIcon = {
            Box(modifier = Modifier.size(56.dp) ) {
                if (currentScreen.route == DriverList.route) {
                    Image(
                        painterResource(id = R.mipmap.ic_launcher_foreground),
                        contentDescription = "logo",
                        modifier = Modifier.fillMaxSize()
                    )
                } else {
                    IconButton(onClick = onBackClicked,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "back")
                    }
                }
            }
        },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary
        )
    )
}