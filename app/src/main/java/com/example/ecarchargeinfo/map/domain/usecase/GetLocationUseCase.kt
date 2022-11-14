package com.example.ecarchargeinfo.map.domain.usecase

import com.example.ecarchargeinfo.map.domain.repository.LocationRepository
import com.google.android.gms.maps.model.LatLng
import javax.inject.Inject

class GetLocationUseCase @Inject constructor(
    private val getLocationRepository: LocationRepository,
) : IGetLocationUseCase {
    override fun invoke(): LatLng = getLocationRepository.getLocation()

}
