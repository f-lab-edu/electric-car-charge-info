package com.example.ecarchargeinfo.map.domain.repository

import android.annotation.SuppressLint
import android.content.Context
import android.location.LocationManager
import com.example.ecarchargeinfo.map.domain.model.MapConstants
import com.google.android.gms.maps.model.LatLng
import javax.inject.Inject

class GetLocationRepositoryImpl @Inject constructor() : GetLocationRepository {
    @SuppressLint("MissingPermission")
    override fun getLocation(context: Context): LatLng {
        var location = LatLng(MapConstants.DEFAULT_LAT, MapConstants.DEFAULT_LONG)
        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val currentLatLng = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
            ?: locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
        currentLatLng?.let {
            location = LatLng(currentLatLng.latitude, currentLatLng.longitude)
        }
        return location
    }
}