package com.alternova.bloodpressure.domain.repository

import com.alternova.bloodpressure.domain.model.BloodPressureMeasurement

interface MeasurementRepository {
    suspend fun saveMeasurement(measurement: BloodPressureMeasurement)
    suspend fun getMeasurements(): List<BloodPressureMeasurement>
}
