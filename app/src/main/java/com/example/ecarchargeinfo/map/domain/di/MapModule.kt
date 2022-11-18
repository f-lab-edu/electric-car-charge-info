package com.example.ecarchargeinfo.map.domain.di

import com.example.ecarchargeinfo.map.domain.repository.chargerinfo.ChargerInfoRepository
import com.example.ecarchargeinfo.map.domain.repository.chargerinfo.ChargerInfoRepositoryImpl
import com.example.ecarchargeinfo.map.domain.repository.geocoder.GeocoderRepository
import com.example.ecarchargeinfo.map.domain.repository.geocoder.GeocoderRepositoryImpl
import com.example.ecarchargeinfo.map.domain.repository.location.LocationRepository
import com.example.ecarchargeinfo.map.domain.repository.location.LocationRepositoryImpl
import com.example.ecarchargeinfo.map.domain.usecase.chargerinfo.GetChargerInfoUseCase
import com.example.ecarchargeinfo.map.domain.usecase.geocoder.GetGeocoderUseCase
import com.example.ecarchargeinfo.map.domain.usecase.location.GetLocationUseCase
import com.example.ecarchargeinfo.map.domain.usecase.chargerinfo.IChargerInfoUseCase
import com.example.ecarchargeinfo.map.domain.usecase.geocoder.IGeocoderUseCase
import com.example.ecarchargeinfo.map.domain.usecase.location.ILocationUseCase
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
    abstract fun bindChangeLocationRepository(repository: ChargerInfoRepositoryImpl): ChargerInfoRepository

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
    abstract fun bindLocationUseCase(useCase: GetLocationUseCase): ILocationUseCase

    @Singleton
    @Binds
    abstract fun bindChangeLocationUseCase(useCase: GetChargerInfoUseCase): IChargerInfoUseCase


}
