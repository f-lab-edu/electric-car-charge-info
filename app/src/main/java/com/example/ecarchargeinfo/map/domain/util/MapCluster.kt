package com.example.ecarchargeinfo.map.domain.util

import android.content.Context
import com.example.ecarchargeinfo.map.domain.model.MapConstants.ChargerStat.CHARGER_STAT_OK
import com.example.ecarchargeinfo.map.domain.model.MapConstants.ChargerStat.CHARGER_STAT_AVAILABLE
import com.example.ecarchargeinfo.map.domain.model.MapConstants.ChargerStat.CHARGER_STAT_ON_UES
import com.example.ecarchargeinfo.map.domain.model.MapConstants.ChargerStat.CHARGER_STAT_CHARGING
import com.example.ecarchargeinfo.map.domain.model.MapConstants.ChargerStat.CHARGER_STAT_BREAK
import com.example.ecarchargeinfo.map.domain.model.MapConstants.ChargerStat.CHARGER_STAT_FAULT_MAINT
import com.example.ecarchargeinfo.map.domain.model.MapConstants.ChargerStat.CHARGER_STAT_NETWORK_ERROR
import com.example.ecarchargeinfo.map.domain.model.MapConstants.ChargerStat.CHARGER_STAT_NETWORK_ERROR_VALUE
import com.example.ecarchargeinfo.map.domain.model.MapConstants.ChargerStat.CHARGER_STAT_NETWORK_DISCONNECT
import com.example.ecarchargeinfo.map.domain.model.MapConstants.ChargerStat.CHARGER_STAT_NETWORK_DISCONNECT_VALUE
import com.example.ecarchargeinfo.map.domain.model.MapConstants.ChargerStat.CHARGER_STAT_EMPTY
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.clustering.ClusterItem
import com.google.maps.android.clustering.view.DefaultClusterRenderer

class MapCluster(
    val _position: LatLng,
    val _title: String,
    val _snippet: String,
    val _icon: BitmapDescriptor,
    val _addr: String,
    val _chargeTp: String,
    val _cpTp: String
) : ClusterItem {
    override fun getPosition(): LatLng = _position
    override fun getTitle(): String = _title
    override fun getSnippet(): String = _snippet
    val icon: BitmapDescriptor
        get() = _icon
    val addr: String
        get() = _addr
    val chargeTp: String
        get() = _chargeTp
    val cpTp: String
        get() = _cpTp
}



