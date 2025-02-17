@file:OptIn(ExperimentalSharedTransitionApi::class)

package com.alternova.bloodpressure.ui.nav

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.alternova.bloodpressure.common.compose.CollectEffect
import com.alternova.bloodpressure.ui.bloodpressureentry.BloodPressureEntryContract
import com.alternova.bloodpressure.ui.bloodpressureentry.BloodPressureEntryScreen
import com.alternova.bloodpressure.ui.bloodpressureentry.BloodPressureEntryViewModel
import com.alternova.bloodpressure.ui.list.BloodPressureListContract
import com.alternova.bloodpressure.ui.list.BloodPressureListScreen
import com.alternova.bloodpressure.ui.list.BloodPressureListViewModel

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

        val viewModel: BloodPressureListViewModel = hiltViewModel()
        CollectEffect(viewModel.navEffect) {
            when(it) {
                BloodPressureListContract.NavEffect.NavToBloodPressureEntry -> {
                    navController.navigate(Nav.Screen.BloodPressureEntry.route)
                }
            }
        }

        sharedTransitionScope.BloodPressureListScreen(
            viewModel = viewModel,
            animatedVisibilityScope = this
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

        val viewModel: BloodPressureEntryViewModel = hiltViewModel()

        CollectEffect(viewModel.navEffect) {
            when(it) {
                BloodPressureEntryContract.NavEffect.NavToBloodPressureList -> {
                    navController.popBackStack()
                }
                BloodPressureEntryContract.NavEffect.NavToBack -> navController.popBackStack()
            }
        }
        
        sharedTransitionScope.BloodPressureEntryScreen(
            viewModel = viewModel,
            animatedVisibilityScope = this
        )
    }
}
