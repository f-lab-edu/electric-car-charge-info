package com.example.ecarchargeinfo.map.domain.usecase

import com.example.ecarchargeinfo.map.domain.repository.GeocoderRepository
import javax.inject.Inject

class GetGeocoderUseCase @Inject constructor(
    private val geocoderRepository: GeocoderRepository
) : IGeocoderUseCase {
    override suspend fun invoke(coords: String): String =
        geocoderRepository.getGeocoder(coords)
}
