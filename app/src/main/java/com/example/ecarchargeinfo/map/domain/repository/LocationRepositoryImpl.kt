package com.example.ecarchargeinfo.map.domain.repository

import android.annotation.SuppressLint
import android.content.Context
import android.location.LocationManager
import com.example.ecarchargeinfo.map.domain.model.MapConstants
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class LocationRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context,
) : LocationRepository {
    @SuppressLint("MissingPermission")
    override fun getLocation(): LatLng {
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