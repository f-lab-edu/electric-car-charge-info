package com.example.ecarchargeinfo.map.domain.usecase.location

import com.example.ecarchargeinfo.map.domain.repository.location.LocationRepository
import com.google.android.gms.maps.model.LatLng
import javax.inject.Inject

class GetLocationUseCase @Inject constructor(
    private val getLocationRepository: LocationRepository,
) : ILocationUseCase {
    override fun invoke(): LatLng = getLocationRepository.getLocation()

}
