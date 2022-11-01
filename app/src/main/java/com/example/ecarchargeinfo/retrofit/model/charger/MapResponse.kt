package com.example.ecarchargeinfo.retrofit.model.charger

data class MapResponse(
    val currentCount: Int,
    val `data`: List<ChargerInfo>,
    val matchCount: Int,
    val page: Int,
    val perPage: Int,
    val totalCount: Int
)
