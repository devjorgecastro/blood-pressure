package com.alternova.bloodpressure.di

import com.alternova.bloodpressure.data.repository.LocalMeasurementRepository
import com.alternova.bloodpressure.domain.repository.MeasurementRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {
    @ViewModelScoped
    @Binds
    abstract fun bindMeasurementRepository(repository: LocalMeasurementRepository): MeasurementRepository
}
