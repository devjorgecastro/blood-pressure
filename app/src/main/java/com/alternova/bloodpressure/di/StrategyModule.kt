package com.alternova.bloodpressure.di

import com.alternova.bloodpressure.data.exception.DataSideEffectStrategy
import com.alternova.bloodpressure.data.exception.MeasurementSideEffectStrategy
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class StrategyModule {

    @Singleton
    @Binds
    abstract fun bindMeasurementSideEffectStrategy(
        strategy: MeasurementSideEffectStrategy
    ): DataSideEffectStrategy
}
