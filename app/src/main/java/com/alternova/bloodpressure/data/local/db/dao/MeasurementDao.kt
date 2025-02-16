package com.alternova.bloodpressure.data.local.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.alternova.bloodpressure.data.local.db.entity.BloodPressureMeasurementEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MeasurementDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(measurement: BloodPressureMeasurementEntity)

    @Query("SELECT * FROM BloodPressureMeasurementEntity ORDER BY dateTime DESC")
    fun getAll(): Flow<List<BloodPressureMeasurementEntity>>
}
