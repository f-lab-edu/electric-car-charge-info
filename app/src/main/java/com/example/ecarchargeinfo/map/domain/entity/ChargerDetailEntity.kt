package com.example.ecarchargeinfo.map.domain.entity

data class ChargerDetailEntity(
    var visible: Boolean,
    var markerInfo: MarkerInfo
)

data class MarkerInfo(
    var cpNm: String,
    var addr: String,
    var chargeTp: String,
    var cpStat: String
)
