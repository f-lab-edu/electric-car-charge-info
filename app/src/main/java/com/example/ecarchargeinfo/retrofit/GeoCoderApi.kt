package com.example.ecarchargeinfo.retrofit

import com.example.ecarchargeinfo.map.domain.model.NaverConstants
import com.example.ecarchargeinfo.retrofit.model.geocoder.GeocoderInfo
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface GeoCoderApi {
    @GET("/map-reversegeocode/v2/gc")
    suspend fun getGeocoder(
        @Header("X-NCP-APIGW-API-KEY-ID") clientId: String = NaverConstants.NAVER_ID,
        @Header("X-NCP-APIGW-API-KEY") clientKey: String = NaverConstants.NAVER_KEY,
        @Query("coords") coords: String,
        @Query("output") output: String = "json",
        @Query("orders") orders: String = "addr",
    ): Response<GeocoderInfo>
}
