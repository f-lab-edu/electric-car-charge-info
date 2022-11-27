package com.example.ecarchargeinfo.info.domain.repository.distance

import com.google.android.gms.maps.model.LatLng

interface DistanceRepository {
    fun getDistance(location: LatLng, chargerLocation: LatLng): Double
}
