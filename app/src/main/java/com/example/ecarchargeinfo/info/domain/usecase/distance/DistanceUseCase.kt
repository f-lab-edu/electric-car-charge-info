package com.example.ecarchargeinfo.info.domain.usecase.distance

import com.example.ecarchargeinfo.info.domain.repository.distance.DistanceRepository
import com.google.android.gms.maps.model.LatLng
import javax.inject.Inject

class DistanceUseCase @Inject constructor(
    private val repository: DistanceRepository
) : IDistanceUseCase {
    override fun invoke(location: LatLng, chargerLocation: LatLng): Double =
        repository.getDistance(location, chargerLocation)
}
