package com.example.ecarchargeinfo.map.domain.usecase

import com.example.ecarchargeinfo.map.domain.repository.GeocoderRepositoryImpl
import javax.inject.Inject

class GetGeocoderUseCase @Inject constructor(
    private val geocoderRepositoryImpl: GeocoderRepositoryImpl
) : IGeocoderUseCase {
    override fun invoke(coords: String): String =
        geocoderRepositoryImpl.getGeocoder(coords)

}
