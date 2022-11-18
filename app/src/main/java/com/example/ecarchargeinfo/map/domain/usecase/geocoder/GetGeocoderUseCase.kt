package com.example.ecarchargeinfo.map.domain.usecase.geocoder

import com.example.ecarchargeinfo.map.domain.repository.geocoder.GeocoderRepository
import javax.inject.Inject

class GetGeocoderUseCase @Inject constructor(
    private val geocoderRepository: GeocoderRepository
) : IGeocoderUseCase {
    override suspend fun invoke(coords: String): String =
        geocoderRepository.getGeocoder(coords)
}
