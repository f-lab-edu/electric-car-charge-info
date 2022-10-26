package com.example.ecarchargeinfo.retrofit

import com.example.ecarchargeinfo.retrofit.model.MapResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface IRetrofit {
    @GET("/api/EvInfoServiceV2/v1/getEvSearchList")
    fun getInfo(
        @Query("page") page: Int,
        @Query("perPage") perPage: Int,
        @Query("cond[addr::LIKE]") cond: String,
        @Query("serviceKey") serviceKey: String
    ): retrofit2.Call<MapResponse>

}
