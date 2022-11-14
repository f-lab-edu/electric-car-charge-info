package com.example.ecarchargeinfo.map.domain.di

import com.example.ecarchargeinfo.map.domain.repository.ChangeLocationRepository
import com.example.ecarchargeinfo.map.domain.repository.ChangeRepositoryImpl
import com.example.ecarchargeinfo.map.domain.repository.GeocoderRepository
import com.example.ecarchargeinfo.map.domain.repository.GeocoderRepositoryImpl
import com.example.ecarchargeinfo.map.domain.repository.LocationRepository
import com.example.ecarchargeinfo.map.domain.repository.LocationRepositoryImpl
import com.example.ecarchargeinfo.map.domain.usecase.ChangeLocationUseCase
import com.example.ecarchargeinfo.map.domain.usecase.GetGeocoderUseCase
import com.example.ecarchargeinfo.map.domain.usecase.GetLocationUseCase
import com.example.ecarchargeinfo.map.domain.usecase.IChangeLocationUseCase
import com.example.ecarchargeinfo.map.domain.usecase.IGeocoderUseCase
import com.example.ecarchargeinfo.map.domain.usecase.IGetLocationUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
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
    abstract fun bindLocationRepository(repository: LocationRepositoryImpl): LocationRepository

    @Singleton
    @Binds
    abstract fun bindGeocoderUseCase(useCase: GetGeocoderUseCase): IGeocoderUseCase

    @Singleton
    @Binds
    abstract fun bindLocationUseCase(useCase: GetLocationUseCase): IGetLocationUseCase

    @Singleton
    @Binds
    abstract fun bindChangeLocationUseCase(useCase: ChangeLocationUseCase): IChangeLocationUseCase

}
