package com.example.ecarchargeinfo.retrofit

import com.example.ecarchargeinfo.retrofit.model.geocoder.GeocoderInfo
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface GeoCoderApi {
    @GET("/map-reversegeocode/v2/gc")
    suspend fun getGeocoder(
        @Header("X-NCP-APIGW-API-KEY-ID") clientId: String,
        @Header("X-NCP-APIGW-API-KEY") clientKey: String,
        @Query("coords") coords: String,
        @Query("output") output: String,
        @Query("orders") orders: String,
    ): Response<GeocoderInfo>
}
