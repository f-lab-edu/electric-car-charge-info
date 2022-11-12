package com.example.ecarchargeinfo.map.domain.di

import android.content.Context
import com.example.ecarchargeinfo.map.domain.repository.*
import com.example.ecarchargeinfo.map.domain.usecase.*
import com.google.android.gms.maps.GoogleMap
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class MapModule {

    @Singleton
    @Binds
    abstract fun bindChangeLocationRepository(repository: ChangeRepositoryImpl): ChangeLocationRepository

    @Singleton
    @Binds
    abstract fun bindGeocoderRepository(repository: GeocoderRepositoryImpl): GeocoderRepository

    @Singleton
    @Binds
    abstract fun bindLocationRepository(repository: LocationRepositoryImpl) : LocationRepository

    @Singleton
    @Binds
    abstract fun bindGeocoderUseCase(useCase: GetGeocoderUseCase) : IGeocoderUseCase

    @Singleton
    @Binds
    abstract fun bindLocationUseCase(useCase: GetLocationUseCase): IGetLocationUseCase

    @Singleton
    @Binds
    abstract fun bindChangeLocationUseCase(useCase: ChangeLocationUseCase): IChangeLocationUseCase

}