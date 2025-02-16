package com.alternova.bloodpressure.ui.nav

object Nav {
    internal sealed class Screen(val route: String) {
        data object BloodPressureList : Screen("blood_pressure_list")
        data object BloodPressureEntry : Screen("blood_pressure_entry")
    }
}
