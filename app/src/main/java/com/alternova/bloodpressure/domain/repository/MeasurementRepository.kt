package com.alternova.bloodpressure.domain.repository

import com.alternova.bloodpressure.domain.model.BloodPressureMeasurement
import kotlinx.coroutines.flow.Flow

interface MeasurementRepository {
    suspend fun saveMeasurement(measurement: BloodPressureMeasurement)
    suspend fun getMeasurements(): Flow<List<BloodPressureMeasurement>>
}
