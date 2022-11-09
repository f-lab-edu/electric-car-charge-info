package com.example.ecarchargeinfo.map.domain.usecase

import com.example.ecarchargeinfo.map.domain.repository.GetGeocoderRepositoryImpl
import javax.inject.Inject

class GetGeocoderUseCase @Inject constructor(
    private val getGeocoderRepositoryImpl: GetGeocoderRepositoryImpl
) : IGetGeocoderUseCase {
    override fun invoke(coords: String): String =
        getGeocoderRepositoryImpl.getGeocoder(coords)

}