package com.dschu.drivermatch.ui.navigation

import androidx.navigation.NavType
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink

interface DriverMatchDestinations {
    val route: String
    val screenTitle: String
}

object DriverList: DriverMatchDestinations {
    override val route = "driverList"
    override val screenTitle = "Driver Match"
}

object LocationMatch: DriverMatchDestinations {
    override val route = "locationMatch"
    override val screenTitle = "Assigned Destination"

    const val driverArg = "driver_name"
    val routeWithArgs = "$route/{$driverArg}"
    val arguments = listOf(
        navArgument(driverArg) {type = NavType.StringType}
    )
    val deepLinks = listOf(
        navDeepLink { uriPattern = "driverMatch://$route/{$driverArg}" }
    )
}

val driverMatchScreens = listOf(DriverList, LocationMatch)