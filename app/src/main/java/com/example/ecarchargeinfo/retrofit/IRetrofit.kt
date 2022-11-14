package com.example.ecarchargeinfo.retrofit

import com.example.ecarchargeinfo.retrofit.model.charger.MapResponse
import com.example.ecarchargeinfo.retrofit.model.geocoder.GeocoderInfo
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface IRetrofit {
    @GET("/api/EvInfoServiceV2/v1/getEvSearchList")
    fun getInfo(
        @Query("page") page: Int,
        @Query("perPage") perPage: Int,
        @Query("cond[addr::LIKE]") cond: String,
        @Query("serviceKey") serviceKey: String
    ): retrofit2.Call<MapResponse>


    @GET("https://naveropenapi.apigw.ntruss.com/map-reversegeocode/v2/gc")
    fun getGeocoder(
        @Header("X-NCP-APIGW-API-KEY-ID") clientId: String,
        @Header("X-NCP-APIGW-API-KEY") clientKey: String,
        @Query("coords") coords: String,
        @Query("output") output: String,
        @Query("orders") orders: String,
    ): retrofit2.Call<GeocoderInfo>

}
