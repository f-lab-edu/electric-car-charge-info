package com.example.ecarchargeinfo.map.domain.repository

import com.example.ecarchargeinfo.map.domain.model.NaverConstants.NAVER_ID
import com.example.ecarchargeinfo.map.domain.model.NaverConstants.NAVER_KEY
import com.example.ecarchargeinfo.retrofit.IRetrofit
import com.example.ecarchargeinfo.retrofit.model.geocoder.GeocoderInfo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class GeocoderRepositoryImpl @Inject constructor(
    private val retrofit: IRetrofit
) : GeocoderRepository {
    override fun getGeocoder(coords: String): String {
        var result = ""
        retrofit.getGeocoder(NAVER_ID, NAVER_KEY, coords, "json", "addr")
            .enqueue(object : Callback<GeocoderInfo> {
                override fun onResponse(
                    call: Call<GeocoderInfo>,
                    response: Response<GeocoderInfo>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            result =
                                it.results[0].region.area1.name + " " + it.results[0].region.area2.name
                            println("성공 : get geo  $result")
                        }
                    }
                }

                override fun onFailure(call: Call<GeocoderInfo>, t: Throwable) {

                }

            })
        return result
    }
}
