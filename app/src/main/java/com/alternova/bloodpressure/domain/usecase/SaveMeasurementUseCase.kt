package com.alternova.bloodpressure.domain.usecase

import com.alternova.bloodpressure.domain.model.BloodPressureMeasurement
import com.alternova.bloodpressure.domain.model.MeasurementState
import com.alternova.bloodpressure.domain.repository.MeasurementRepository
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class SaveMeasurementUseCase @Inject constructor(
    private val repository: MeasurementRepository
) {
    suspend operator fun invoke(
        systolicPressure: Int,
        diastolicPressure: Int,
        measurementState: MeasurementState
    ) {
        val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")
        val formattedDate = LocalDateTime.now().format(formatter)
        val measurement = BloodPressureMeasurement(
            systolic = systolicPressure,
            diastolic = diastolicPressure,
            date = formattedDate,
            state = measurementState
        )
        repository.saveMeasurement(measurement)
    }
}
