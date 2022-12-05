package com.example.ecarchargeinfo.main.presentation.input

import android.widget.EditText
import com.example.ecarchargeinfo.main.domain.entity.MainSearchFilterSpeedEntity
import com.example.ecarchargeinfo.map.domain.entity.MarkerInfo
import com.example.ecarchargeinfo.map.presentation.ui.MapFragment
import com.google.android.gms.maps.model.LatLng

interface MainInputs {
    fun onComboClick()
    fun onDemoClick()
    fun onACClick()
    fun onSlowClick()
    fun onSpeedClick()
    fun onSpeedChange(thisSpeedEntity: MainSearchFilterSpeedEntity)
    fun onMarkerClick(visible: Boolean, markerInfo: MarkerInfo)
    fun onMarkerClick(visible: Boolean)
    fun onSearchButtonClick(searchTxt: String)
    fun onMoveCamera(position: LatLng, zoom: Float)
    fun onDetailClick()
}
