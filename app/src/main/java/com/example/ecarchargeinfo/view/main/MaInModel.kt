package com.example.ecarchargeinfo.view.main

class MaInModel {
    var combo = true
    var demo = true
    var ac = true
    var slow = false
    var speed = false
    var startRange = 50
    var endRange = 350

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

    fun setRange(num1: Int, num2: Int) {
        startRange = num1
        endRange = num2
    }

    fun getRange() = arrayOf(startRange, endRange)

}
