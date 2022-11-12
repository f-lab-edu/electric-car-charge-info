package com.example.ecarchargeinfo.map.domain.repository

import com.example.ecarchargeinfo.R
import com.example.ecarchargeinfo.config.ApplicationClass.Companion.sRetrofit
import com.example.ecarchargeinfo.retrofit.IRetrofit
import com.example.ecarchargeinfo.retrofit.model.geocoder.GeocoderInfo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class GeocoderRepositoryImpl @Inject constructor() : GeocoderRepository {
    override fun getGeocoder(coords: String): String {
        var result = ""
       val service = sRetrofit.create(IRetrofit::class.java)
        service.getGeocoder(
            R.string.naver_client_id.toString(),R.string.naver_client_key.toString(),
            coords, "json", "addr").enqueue(object : Callback<GeocoderInfo>{
            override fun onResponse(call: Call<GeocoderInfo>, response: Response<GeocoderInfo>) {
                if (response.isSuccessful)  {
                    response.body()?.let {
                        result = it.results[0].region.area1.name+ " " + it.results[0].region.area2.name
                        println("#############"+ result)
                    }
                }
            }
            override fun onFailure(call: Call<GeocoderInfo>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })

        return result
    }
}