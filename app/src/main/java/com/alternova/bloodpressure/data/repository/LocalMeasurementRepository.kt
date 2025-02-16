package com.alternova.bloodpressure.data.repository

import com.alternova.bloodpressure.data.local.db.dao.MeasurementDao
import com.alternova.bloodpressure.data.local.db.entity.toDomain
import com.alternova.bloodpressure.data.local.db.entity.toEntity
import com.alternova.bloodpressure.domain.model.BloodPressureMeasurement
import com.alternova.bloodpressure.domain.repository.MeasurementRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LocalMeasurementRepository @Inject constructor(
    private val dao: MeasurementDao
): MeasurementRepository {
    override suspend fun saveMeasurement(measurement: BloodPressureMeasurement) {
        dao.insert(measurement.toEntity())
    }

    override suspend fun getMeasurements(): Flow<List<BloodPressureMeasurement>> {
        return dao.getAll().map { it.map { it.toDomain() } }
    }
}
