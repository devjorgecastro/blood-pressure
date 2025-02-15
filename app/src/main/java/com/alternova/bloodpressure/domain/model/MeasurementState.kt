package com.alternova.bloodpressure.domain.model

sealed class MeasurementState {
    data object Rest : MeasurementState()
    data object LightActivity : MeasurementState()
    data object IntenseActivity : MeasurementState()
}
