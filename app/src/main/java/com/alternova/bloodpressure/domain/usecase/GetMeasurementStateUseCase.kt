package com.alternova.bloodpressure.domain.usecase

import com.alternova.bloodpressure.domain.model.MeasurementState
import javax.inject.Inject

class GetMeasurementStateUseCase @Inject constructor() {
    operator fun invoke(): List<MeasurementState> = listOf(
        MeasurementState.Rest,
        MeasurementState.LightActivity,
        MeasurementState.IntenseActivity
    )
}
