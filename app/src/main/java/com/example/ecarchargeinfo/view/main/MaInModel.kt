package com.example.ecarchargeinfo.view.main

class MaInModel {
     var combo = true
     var demo = true
     var ac = true
     var slow = false
     var speed = false

    fun getType(type: String): Boolean = when (type) {
        "combo" -> combo
        "demo" -> demo
        "ac" -> ac
        "slow" -> slow
        "speed" -> speed
        else -> false
    }

    fun changeTypeState(type: String) {
        when (type) {
            "combo" -> combo = !combo
            "demo" -> demo = !demo
            "ac" -> ac = !ac
            "slow" -> slow = !slow
            "speed" -> speed = !speed
        }
    }

}
