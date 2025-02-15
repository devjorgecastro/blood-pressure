package com.alternova.bloodpressure.domain.model

data class BloodPressureMeasurement(
    val systolic: Int,
    val diastolic: Int,
    val dateTime: Long,
    val state: MeasurementState
)
