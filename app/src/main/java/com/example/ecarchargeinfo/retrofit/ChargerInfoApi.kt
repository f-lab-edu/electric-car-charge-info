package com.example.ecarchargeinfo.retrofit

import com.example.ecarchargeinfo.retrofit.model.charger.MapResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ChargerInfoApi {
    @GET("/api/EvInfoServiceV2/v1/getEvSearchList")
    suspend fun getChargerInfo(
        @Query("page") page: Int,
        @Query("perPage") perPage: Int,
        @Query("cond[addr::LIKE]") cond: String,
        @Query("serviceKey") serviceKey: String
    ): Response<MapResponse>
}
