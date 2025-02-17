package com.alternova.bloodpressure.data.local.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.alternova.bloodpressure.domain.model.BloodPressureMeasurement
import com.alternova.bloodpressure.domain.model.MeasurementState

@Entity
data class BloodPressureMeasurementEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val systolic: Int,
    val diastolic: Int,
    val date: String,
    val state: String
)

fun BloodPressureMeasurement.toEntity() = BloodPressureMeasurementEntity(
    systolic = this.systolic,
    diastolic = this.diastolic,
    date = this.date,
    state = this.state::class.simpleName ?: ""
)

fun BloodPressureMeasurementEntity.toDomain() = BloodPressureMeasurement(
    systolic = this.systolic,
    diastolic = this.diastolic,
    date = this.date,
    state = when (this.state) {
        "Rest" -> MeasurementState.Rest
        "LightActivity" -> MeasurementState.LightActivity
        "IntenseActivity" -> MeasurementState.IntenseActivity
        else -> MeasurementState.Rest
    }
)