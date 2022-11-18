package com.example.ecarchargeinfo.map.domain.repository.geocoder

interface GeocoderRepository {
    suspend fun getGeocoder(coords: String): String
}
