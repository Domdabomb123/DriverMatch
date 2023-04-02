package com.dschu.drivermatch.ui.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import com.dschu.drivermatch.viewmodel.DriverMatchVM
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.dschu.drivermatch.ui.screens.DriverListScreen
import com.dschu.drivermatch.ui.screens.LocationMatchScreen
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable

@OptIn(ExperimentalAnimationApi::class, ExperimentalFoundationApi::class)
@Composable
fun DriverMatchNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    viewModel: DriverMatchVM
) {
    AnimatedNavHost(
        navController = navController,
        startDestination = DriverList.route,
        modifier = modifier
    ) {
        composable(
            route = DriverList.route,
            enterTransition = {
                slideInHorizontally(initialOffsetX = { 600 })
            },
            popEnterTransition = {
                slideInHorizontally(initialOffsetX = { 600 })
            }
        ) {
            DriverListScreen(
                viewModel = viewModel,
                onDriverClicked = { driver ->
                    navController.navigateToLocationMatch(driver)
                }
            )
        }
        composable(
            route = LocationMatch.routeWithArgs,
            arguments = LocationMatch.arguments,
            deepLinks = LocationMatch.deepLinks,
            enterTransition = {
                slideInHorizontally(initialOffsetX = { 600 })
            },
            popEnterTransition = {
                slideInHorizontally(initialOffsetX = { 600 })
            }
        ) { navBackStackEntry ->
            navBackStackEntry.arguments?.getString(LocationMatch.driverArg)?.let { driver ->
                LocationMatchScreen(
                    viewModel = viewModel,
                    driver = driver
                )
            }
        }
    }
}

fun NavHostController.navigateSingleTopTo(route: String) =
    this.navigate(route) {
        popUpTo(
            this@navigateSingleTopTo.currentBackStackEntry?.id ?: return@navigate
        ) {
            inclusive = true
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }

private fun NavHostController.navigateToLocationMatch(driver: String) {
    this.navigateSingleTopTo("${LocationMatch.route}/$driver")
}