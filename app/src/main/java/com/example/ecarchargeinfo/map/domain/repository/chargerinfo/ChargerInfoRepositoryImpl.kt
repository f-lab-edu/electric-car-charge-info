package com.example.ecarchargeinfo.map.domain.repository.chargerinfo

import android.content.res.Resources
import com.bumptech.glide.load.engine.Resource
import com.example.ecarchargeinfo.main.presentation.output.MainChargerInfoState
import com.example.ecarchargeinfo.map.domain.model.NaverConstants.CHARGER_KEY
import com.example.ecarchargeinfo.retrofit.ChargerInfoApi
import com.example.ecarchargeinfo.retrofit.model.charger.ChargerInfo
import com.example.ecarchargeinfo.retrofit.model.charger.MapResponse
import javax.inject.Inject

class ChargerInfoRepositoryImpl @Inject constructor(
    private val retrofit: ChargerInfoApi
) : ChargerInfoRepository {
    override suspend fun getChargerInfo(cond: String): List<ChargerInfo> {
        retrofit.getChargerInfo(1, 100, cond = cond, CHARGER_KEY).body()?.let {
            return it.data
        }
        return emptyList()
    }
}
