package com.example.ecarchargeinfo.map.domain.repository

interface GeocoderRepository {
    suspend fun getGeocoder(coords: String): String
}
