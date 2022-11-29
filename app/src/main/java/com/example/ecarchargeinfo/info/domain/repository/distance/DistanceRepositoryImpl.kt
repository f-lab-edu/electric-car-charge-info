package com.example.ecarchargeinfo.info.domain.repository.distance

import android.location.Location
import com.google.android.gms.maps.model.LatLng
import javax.inject.Inject
import kotlin.math.round

class DistanceRepositoryImpl @Inject constructor() : DistanceRepository {
    override fun getDistance(location: LatLng, chargerLocation: LatLng): Double {
        val location = Location("").also {
            it.latitude = location.latitude
            it.longitude = location.longitude
        }
        val chargerLocation = Location("").also {
            it.latitude = chargerLocation.latitude
            it.longitude = chargerLocation.longitude
        }

        return round(location.distanceTo(chargerLocation).toDouble() / 1000 * 100) / 100
    }

}
