package com.example.ecarchargeinfo.main.domain.entity

data class MainSearchFilterEntity(
    var combo: Boolean,
    var demo: Boolean,
    var ac: Boolean,
    var slow: Boolean,
    var speedEntity: MainSearchFilterSpeedEntity
)

data class MainSearchFilterSpeedEntity(
    var speed: Boolean,
    var startRange: Int,
    var endRange: Int
)
