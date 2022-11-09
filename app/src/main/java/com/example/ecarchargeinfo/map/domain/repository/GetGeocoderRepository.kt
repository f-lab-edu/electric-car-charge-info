package com.example.ecarchargeinfo.map.domain.repository

interface GetGeocoderRepository {
    fun getGeocoder(coords: String): String
}