package com.alternova.bloodpressure.domain.model

sealed class MeasurementState(val description: String) {
    data object Rest : MeasurementState("Rest")
    data object LightActivity : MeasurementState("Light Activity")
    data object IntenseActivity : MeasurementState("Intense Physical Activity")
}
