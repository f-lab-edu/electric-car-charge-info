package com.example.ecarchargeinfo.info.domain.di

import com.example.ecarchargeinfo.info.domain.repository.copyaddress.CopyAddressRepository
import com.example.ecarchargeinfo.info.domain.repository.copyaddress.CopyAddressRepositoryImpl
import com.example.ecarchargeinfo.info.domain.repository.distance.DistanceRepository
import com.example.ecarchargeinfo.info.domain.repository.distance.DistanceRepositoryImpl
import com.example.ecarchargeinfo.info.domain.usecase.copyaddress.CopyAddressUseCase
import com.example.ecarchargeinfo.info.domain.usecase.copyaddress.ICopyAddressUseCase
import com.example.ecarchargeinfo.info.domain.usecase.distance.DistanceUseCase
import com.example.ecarchargeinfo.info.domain.usecase.distance.IDistanceUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class InfoModule {

    @Singleton
    @Binds
    abstract fun bindCopyAddressRepository(repository: CopyAddressRepositoryImpl) : CopyAddressRepository

    @Singleton
    @Binds
    abstract fun bindCopyAddressUseCase(useCase: CopyAddressUseCase) : ICopyAddressUseCase

    @Singleton
    @Binds
    abstract fun bindDistanceRepository(repository: DistanceRepositoryImpl) : DistanceRepository

    @Singleton
    @Binds
    abstract fun bindDistanceUseCase(useCase: DistanceUseCase): IDistanceUseCase
}
