package com.example.ecarchargeinfo.map.domain.repository.chargerinfo

import com.example.ecarchargeinfo.retrofit.model.charger.ChargerInfo

interface ChargerInfoRepository {
    suspend fun getChargerInfo(cond: String): List<ChargerInfo>
}
