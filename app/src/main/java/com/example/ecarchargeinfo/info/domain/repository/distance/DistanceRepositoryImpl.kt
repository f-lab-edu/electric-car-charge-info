package com.example.ecarchargeinfo.info.domain.repository.distance

import com.example.ecarchargeinfo.info.domain.model.DistanceConstants
import com.google.android.gms.maps.model.LatLng
import javax.inject.Inject
import kotlin.math.asin
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.round
import kotlin.math.sin
import kotlin.math.sqrt

class DistanceRepositoryImpl @Inject constructor(): DistanceRepository {
    override fun getDistance(location: LatLng, chargerLocation: LatLng): Double {
        val dLat = Math.toRadians(chargerLocation.latitude - location.latitude)
        val dLon = Math.toRadians(chargerLocation.longitude - location.longitude)
        val a = sin(dLat/2).pow(2.0)+ sin(dLon/2).pow(2.0)* cos(Math.toRadians(location.latitude)* cos(Math.toRadians(chargerLocation.latitude)))
        val c = 2* asin(sqrt(a))
        val result = (DistanceConstants.R * c.toDouble()).toInt()
        val distance = result.toString()
        return round(distance.toDouble()/1000 * 100)/ 100
    }
}
