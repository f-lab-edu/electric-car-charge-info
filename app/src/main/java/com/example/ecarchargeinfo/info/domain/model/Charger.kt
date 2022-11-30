package com.example.ecarchargeinfo.info.domain.model

import com.example.ecarchargeinfo.info.domain.enum.ChargerStat

data class Charger(
    val chargeTp : String,
    val cpStat: ChargerStat,
    val cpTp: String
)
