package com.alternova.bloodpressure.domain.usecase

import com.alternova.bloodpressure.domain.model.BloodPressureMeasurement
import com.alternova.bloodpressure.domain.repository.MeasurementRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMeasurementsUseCase @Inject constructor(
    private val repository: MeasurementRepository
) {
    suspend operator fun invoke(): Flow<List<BloodPressureMeasurement>> {
        return repository.getMeasurements()
    }
}
