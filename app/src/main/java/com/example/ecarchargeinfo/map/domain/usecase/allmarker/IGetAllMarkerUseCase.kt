package com.example.ecarchargeinfo.map.domain.usecase.allmarker

import com.example.ecarchargeinfo.map.domain.util.MapCluster
import com.example.ecarchargeinfo.retrofit.model.charger.ChargerInfo

interface IGetAllMarkerUseCase {
    operator fun invoke(list :List<ChargerInfo>): List<MapCluster>
}
