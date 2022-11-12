package com.example.ecarchargeinfo.map.domain.repository

import com.google.android.gms.maps.model.LatLng

interface LocationRepository {
    fun getLocation(): LatLng
}