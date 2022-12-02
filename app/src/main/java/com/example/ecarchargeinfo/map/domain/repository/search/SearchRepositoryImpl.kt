package com.example.ecarchargeinfo.map.domain.repository.search

import com.example.ecarchargeinfo.map.domain.entity.MarkerInfo
import com.example.ecarchargeinfo.retrofit.ChargerInfoApi
import retrofit2.Retrofit
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val retrofitBuilder: ChargerInfoApi
): SearchRepository {


}
