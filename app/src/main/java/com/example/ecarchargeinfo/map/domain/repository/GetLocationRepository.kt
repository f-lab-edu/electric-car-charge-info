package com.example.ecarchargeinfo.map.domain.repository

import android.content.Context
import com.google.android.gms.maps.model.LatLng

interface GetLocationRepository {
    fun getLocation(): LatLng
}