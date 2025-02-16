@file:OptIn(ExperimentalSharedTransitionApi::class)

package com.alternova.bloodpressure.ui.nav

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.alternova.bloodpressure.ui.bloodpressureentry.BloodPressureEntryScreen
import com.alternova.bloodpressure.ui.list.BloodPressureListScreen

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
    sharedTransitionScope: SharedTransitionScope
) {
    NavHost(
        modifier = modifier,
        navController = navHostController,
        startDestination = Nav.Screen.BloodPressureList.route
    ) {
        bloodPressureList(
            navController = navHostController,
            sharedTransitionScope = sharedTransitionScope
        )

        bloodPressureEntryScreen(
            navController = navHostController,
            sharedTransitionScope = sharedTransitionScope
        )
    }
}

private fun NavGraphBuilder.bloodPressureList(
    navController: NavHostController,
    sharedTransitionScope: SharedTransitionScope
) {
    composable(
        route = Nav.Screen.BloodPressureList.route
    ) {
        sharedTransitionScope.BloodPressureListScreen(
            animatedVisibilityScope = this,
            onNewBloodPressureClick = {
                navController.navigate(Nav.Screen.BloodPressureEntry.route)
            }
        )
    }
}

private fun NavGraphBuilder.bloodPressureEntryScreen(
    navController: NavHostController,
    sharedTransitionScope: SharedTransitionScope
) {
    composable(
        route = Nav.Screen.BloodPressureEntry.route
    ) {
        sharedTransitionScope.BloodPressureEntryScreen(
            animatedVisibilityScope = this
        )
    }
}
