package com.alternova.bloodpressure.domain.usecase

import com.alternova.bloodpressure.domain.model.BloodPressureMeasurement
import com.alternova.bloodpressure.domain.repository.MeasurementRepository

class SaveMeasurementUseCase(private val repository: MeasurementRepository) {
    suspend fun execute(measurement: BloodPressureMeasurement) {
        repository.saveMeasurement(measurement)
    }
}
