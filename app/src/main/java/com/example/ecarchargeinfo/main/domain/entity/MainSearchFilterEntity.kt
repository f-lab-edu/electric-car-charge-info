package com.example.ecarchargeinfo.main.domain.entity

data class MainSearchFilterEntity(
    var combo: Boolean = true,
    var demo: Boolean = true,
    var ac: Boolean = true,
    var slow: Boolean = false,
    var speedEntity: MainSearchFilterSpeedEntity = MainSearchFilterSpeedEntity()
)

data class MainSearchFilterSpeedEntity(
    var speed: Boolean = false,
    var startRange: Int = 50,
    var endRange: Int = 350
)
