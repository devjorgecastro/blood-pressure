package com.alternova.bloodpressure.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.alternova.bloodpressure.data.local.db.dao.MeasurementDao
import com.alternova.bloodpressure.data.local.db.entity.BloodPressureMeasurementEntity

@Database(
    entities = [
        BloodPressureMeasurementEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase: RoomDatabase() {
    abstract fun measurementDao(): MeasurementDao
}

object AppDatabaseConstants {
    const val DATABASE_NAME = "recipe-database"
}
