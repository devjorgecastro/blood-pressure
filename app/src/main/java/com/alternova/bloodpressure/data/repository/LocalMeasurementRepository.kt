package com.alternova.bloodpressure.data.repository

import android.database.sqlite.SQLiteAccessPermException
import com.alternova.bloodpressure.data.exception.DataSideEffectStrategy
import com.alternova.bloodpressure.data.local.db.dao.MeasurementDao
import com.alternova.bloodpressure.data.local.db.entity.toDomain
import com.alternova.bloodpressure.data.local.db.entity.toEntity
import com.alternova.bloodpressure.domain.model.BloodPressureMeasurement
import com.alternova.bloodpressure.domain.repository.BaseRepository
import com.alternova.bloodpressure.domain.repository.MeasurementRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LocalMeasurementRepository @Inject constructor(
    private val dao: MeasurementDao,
    private val dataSideEffectStrategy: DataSideEffectStrategy,
): BaseRepository(), MeasurementRepository {

    private val isErrorSimulation = false

    override suspend fun saveMeasurement(measurement: BloodPressureMeasurement) {
        execute(dataErrorStrategy = dataSideEffectStrategy) {
            if (isErrorSimulation){
                throw SQLiteAccessPermException()
            }
            else {
                dao.insert(measurement.toEntity())
            }
        }
    }

    override suspend fun getMeasurements(): Flow<List<BloodPressureMeasurement>> {
        return executeFlow(dataErrorStrategy = dataSideEffectStrategy) {
            if (isErrorSimulation) {
                flow { throw Throwable("This is a error simulation") }
            }
            else {
                dao.getAll().map { it.map { item -> item.toDomain() } }
            }
        }
    }
}
