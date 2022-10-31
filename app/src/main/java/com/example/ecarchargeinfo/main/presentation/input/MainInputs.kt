package com.example.ecarchargeinfo.main.presentation.input

import android.app.Activity
import android.content.Context
import com.example.ecarchargeinfo.main.domain.entity.MainSearchFilterSpeedEntity
import com.google.android.gms.maps.model.LatLng

interface MainInputs {
    fun onComboClick()
    fun onDemoClick()
    fun onACClick()
    fun onSlowClick()
    fun onSpeedClick()
    fun onSpeedChange(thisSpeedEntity: MainSearchFilterSpeedEntity)
    fun getLatLng(context: Context): LatLng?
}
