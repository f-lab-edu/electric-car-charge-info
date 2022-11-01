package com.example.ecarchargeinfo.retrofit.model.charger

data class ChargerInfo(
    val addr: String,
    val chargeTp: String,
    val cpId: Int,
    val cpNm: String,
    val cpStat: String,
    val cpTp: String,
    val csId: Int,
    val csNm: String,
    val lat: String,
    val longi: String,
    val statUpdatetime: String
)
