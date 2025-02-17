package com.alternova.bloodpressure.domain.model

enum class BloodPressureCategory(
    val color: Long = 0XFF4CAF50
) {
    HYPERTENSIVE_CRISIS(color = 0XFFB71C1C),
    HYPERTENSION_STAGE_1(color = 0xFFFF9800),
    HYPERTENSION_STAGE_2(color = 0xFFF44336),
    ELEVATED_PRESSURE(color = 0xFFFFB300),
    NORMAL_PRESSURE(color = 0XFF4CAF50)
}
