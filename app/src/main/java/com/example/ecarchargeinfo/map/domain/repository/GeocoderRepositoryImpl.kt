package com.example.ecarchargeinfo.map.domain.repository

import com.example.ecarchargeinfo.map.domain.model.NaverConstants.NAVER_ID
import com.example.ecarchargeinfo.map.domain.model.NaverConstants.NAVER_KEY
import com.example.ecarchargeinfo.retrofit.GeoCoderApi
import javax.inject.Inject

class GeocoderRepositoryImpl @Inject constructor(
    private val retrofit: GeoCoderApi
) : GeocoderRepository {
    override suspend fun getGeocoder(coords: String): String {
        retrofit.getGeocoder(NAVER_ID, NAVER_KEY, coords, "json", "addr").body()?.let {
            return (it.results[0].region.area1.name + " " + it.results[0].region.area2.name)
        }
        return ""

    }
}
