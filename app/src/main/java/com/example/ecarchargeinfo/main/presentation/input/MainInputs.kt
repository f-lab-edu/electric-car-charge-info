package com.example.ecarchargeinfo.main.presentation.input

import com.example.ecarchargeinfo.main.domain.entity.MainSearchFilterSpeedEntity
import com.example.ecarchargeinfo.map.domain.entity.MarkerInfo

interface MainInputs {
    fun onComboClick()
    fun onDemoClick()
    fun onACClick()
    fun onSlowClick()
    fun onSpeedClick()
    fun onSpeedChange(thisSpeedEntity: MainSearchFilterSpeedEntity)
    fun onMarkerClick(visible: Boolean, markerInfo: MarkerInfo)
    fun onMarkerClick(visible: Boolean)
}
