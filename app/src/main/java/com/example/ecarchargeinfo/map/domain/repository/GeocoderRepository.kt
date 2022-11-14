package com.example.ecarchargeinfo.map.domain.repository

interface GeocoderRepository {
    fun getGeocoder(coords: String): String
}
