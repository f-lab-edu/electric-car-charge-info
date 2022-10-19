package com.example.ecarchargeinfo.main.presentation.input

import com.example.ecarchargeinfo.main.domain.entity.MainSearchFilterSpeedEntity
import com.google.android.material.slider.RangeSlider

interface MainInputs {
    fun onComboClick()
    fun onDemoClick()
    fun onACClick()
    fun onSlowClick()
    fun onSpeedClick()
    fun onSpeedChange(thisSpeedEntity: MainSearchFilterSpeedEntity)
}
