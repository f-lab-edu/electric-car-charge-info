package com.example.ecarchargeinfo.map.domain.repository.geocoder

import com.example.ecarchargeinfo.retrofit.GeoCoderApi
import javax.inject.Inject

class GeocoderRepositoryImpl @Inject constructor(
    private val retrofit: GeoCoderApi
) : GeocoderRepository {
    override suspend fun getGeocoder(coords: String): String {
        val test = retrofit.getGeocoder(coords = coords)
        try {
            if (test.isSuccessful) {
                test.body()?.let {
                    if (it.results[0].name != "no results") {
                        println("@@geo"+it.results[0].region.area1.name + " " + it.results[0].region.area2.name)
                        return (it.results[0].region.area1.name + " " + it.results[0].region.area2.name)
                    }
                }
            }
        } catch (e: Exception) {

        }

/*
        retrofit.getGeocoder(coords = coords).body()?.let {
            return (it.results[0].region.area1.name + " " + it.results[0].region.area2.name)
        }
*/
        return ""

    }
}
