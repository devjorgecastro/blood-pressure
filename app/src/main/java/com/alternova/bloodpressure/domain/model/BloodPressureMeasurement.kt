package com.alternova.bloodpressure.domain.model

data class BloodPressureMeasurement(
    val systolic: Int,
    val diastolic: Int,
    val date: String,
    val state: MeasurementState
) {
    fun getBloodPressureCategory(): BloodPressureCategory {
        return when {
            systolic >= 180 || diastolic >= 120 -> BloodPressureCategory.HYPERTENSIVE_CRISIS
            systolic >= 140 || diastolic >= 90 -> BloodPressureCategory.HYPERTENSION_STAGE_2
            systolic in 130..139 || diastolic in 80..89 -> BloodPressureCategory.HYPERTENSION_STAGE_1
            systolic in 120..129 && diastolic < 80 -> BloodPressureCategory.ELEVATED_PRESSURE
            else -> BloodPressureCategory.NORMAL_PRESSURE
        }
    }
}
