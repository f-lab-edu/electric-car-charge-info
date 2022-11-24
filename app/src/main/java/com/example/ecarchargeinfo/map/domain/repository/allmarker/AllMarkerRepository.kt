package com.example.ecarchargeinfo.map.domain.repository.allmarker

import com.example.ecarchargeinfo.map.domain.util.MyItem
import com.example.ecarchargeinfo.retrofit.model.charger.ChargerInfo

interface AllMarkerRepository {
    fun getAllMarker(list: List<ChargerInfo>): ArrayList<MyItem>
}
