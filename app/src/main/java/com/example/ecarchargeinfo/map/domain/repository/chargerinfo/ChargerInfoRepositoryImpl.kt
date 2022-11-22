package com.example.ecarchargeinfo.map.domain.repository.chargerinfo

import com.example.ecarchargeinfo.retrofit.ChargerInfoApi
import com.example.ecarchargeinfo.retrofit.model.charger.ChargerInfo
import javax.inject.Inject

class ChargerInfoRepositoryImpl @Inject constructor(
    private val retrofit: ChargerInfoApi
) : ChargerInfoRepository {
    override suspend fun getChargerInfo(cond: String): List<ChargerInfo> {
        retrofit.getChargerInfo( cond = cond).body()?.let {
            return it.data
        }
        return emptyList()
    }
}
