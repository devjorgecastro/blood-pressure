package com.alternova.bloodpressure.di

import android.content.Context
import androidx.room.Room
import com.alternova.bloodpressure.data.local.db.AppDatabase
import com.alternova.bloodpressure.data.local.db.AppDatabaseConstants
import com.alternova.bloodpressure.data.local.db.dao.MeasurementDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            AppDatabaseConstants.DATABASE_NAME
        ).build()

    @Singleton
    @Provides
    fun provideMeasurementDao(database: AppDatabase): MeasurementDao = database.measurementDao()
}
