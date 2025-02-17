package com.alternova.bloodpressure

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.alternova.bloodpressure.ui.nav.AppNavHost
import com.alternova.bloodpressure.ui.theme.BloodPressureTheme
import dagger.hilt.android.AndroidEntryPoint

const val FAB_EXPLODE_BOUNDS_KEY = "FAB_EXPLODE_BOUNDS_KEY"

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalSharedTransitionApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BloodPressureTheme(
                dynamicColor = false
            ) {
                val navHostController = rememberNavController()
                SharedTransitionLayout {
                    AppNavHost(
                        navHostController = navHostController,
                        sharedTransitionScope = this
                    )
                }
            }
        }
    }
}
