package com.example.ecarchargeinfo.main.presentation.input

import com.example.ecarchargeinfo.main.domain.entity.MainSearchFilterSpeedEntity

interface MainInputs {
    fun onComboClick()
    fun onDemoClick()
    fun onACClick()
    fun onSlowClick()
    fun onSpeedClick()
    fun onSpeedChange(thisSpeedEntity: MainSearchFilterSpeedEntity)
}
