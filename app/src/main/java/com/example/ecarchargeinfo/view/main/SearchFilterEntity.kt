package com.example.ecarchargeinfo.view.main

data class SearchFilterEntity(
    var combo: Boolean = true,
    var demo: Boolean = true,
    var ac: Boolean = true,
    var slow: Boolean = false,
    var speed: Boolean = false,
    var startRange: Int = 50,
    var endRange: Int = 350
)
